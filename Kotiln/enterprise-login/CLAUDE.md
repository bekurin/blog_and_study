# 엔터프라이즈 로그인 시스템 — 프로젝트 요구사항

## 프로젝트 개요

웹 브라우저에서 ID/비밀번호 입력 없이 로그인하는 엔터프라이즈 인증 시스템.
두 가지 로그인 방식을 병행 지원한다.

| 방식 | 설명 | 주요 사용자 |
|---|---|---|
| **QR 코드 로그인** | 모바일 앱으로 QR 스캔하여 인증 | 모바일 앱 보유자 |
| **WebAuthn Passkey** | Touch ID / Face ID / YubiKey로 인증 | 등록된 디바이스 보유자 |

---

## 기술 스택

### Backend
- **언어**: Java 17+
- **프레임워크**: Spring Boot 3.4+, Spring Security 6.4+
- **WebAuthn 라이브러리**: Spring Security 내장 WebAuthn (내부적으로 webauthn4j 사용)
- **토큰**: JWT (RS256), Opaque Refresh Token
- **캐시/세션**: Redis
- **DB**: JPA (구체적인 DB는 미정)

### Infrastructure (AWS)
- **JWKS 배포**: S3 + CloudFront
- **Private Key 보관**: AWS KMS 또는 CloudHSM (소프트웨어 키 파일 금지)
- **Refresh Token 저장소**: Redis (TTL = 7일)

### 관련 RFC
- RFC 8628 — OAuth 2.0 Device Authorization Grant (QR 로그인)
- RFC 6749 — OAuth 2.0 (토큰 발급 기반)
- RFC 7519 — JWT
- RFC 7517 — JWK

---

## 핵심 제약사항 (절대 위반 금지)

1. **웹 페이지에서 ID/비밀번호 입력 폼을 제공하지 않는다.**
2. **JWT 서명에 대칭 키(HS256)를 사용하지 않는다.** RSA 비대칭 키(RS256)만 사용.
3. **RSA Private Key를 코드나 설정 파일에 하드코딩하지 않는다.** AWS KMS 사용.
4. **JWKS S3 버킷을 퍼블릭으로 열지 않는다.** CloudFront OAC를 통해서만 접근.
5. **Refresh Token을 JavaScript에서 접근 가능한 곳에 저장하지 않는다.** httpOnly Cookie만.

---

## 인증 방식 1: QR 코드 로그인 (RFC 8628 Device Authorization Grant)

### 플로우 요약

```
1. Web → Auth Server: POST /oauth2/device_authorization
2. Auth Server → Web: device_code, verification_uri_complete, expires_in=600, interval=5
3. Web: verification_uri_complete를 QR 코드로 렌더링 + 만료 카운트다운 표시
4. Web: POST /oauth2/token 폴링 시작 (interval=5초 준수)
5. Mobile App: QR 스캔 → verification_uri_complete 접근 → 기존 세션으로 디바이스 승인
6. Auth Server: 승인 처리
7. Web 폴링: 200 응답 수신 → access_token + refresh_token 저장
```

### 폴링 응답 처리

| 오류 코드 | 처리 |
|---|---|
| `authorization_pending` | interval 후 재시도 |
| `slow_down` | interval + 5초 후 재시도 |
| `access_denied` | 로그인 실패 UI |
| `expired_token` | QR 재발급 안내 |

### 구현 엔드포인트

```
POST /oauth2/device_authorization    # 디바이스 코드 발급
POST /oauth2/token                   # 토큰 폴링 + Refresh Token 갱신
POST /oauth2/device_approve          # 모바일 앱의 디바이스 승인 (모바일 Access Token 인증 필요)
```

---

## 인증 방식 2: WebAuthn Passkey (Spring Security 6.4 내장)

### Spring Security 설정 방향

`http.webAuthn(...)` DSL을 사용한다. Spring Security가 아래 엔드포인트를 자동 생성한다.

```
POST /webauthn/register/options    # 등록 챌린지 발급 (자동)
POST /webauthn/register            # 등록 완료 (자동)
POST /login/options                # 인증 챌린지 발급 (자동)
POST /login                        # Passkey 로그인 (자동)
```

### 구현할 인터페이스 (필수)

| 인터페이스 | 역할 |
|---|---|
| `PublicKeyCredentialUserEntityRepository` | 사용자 ↔ WebAuthn UserEntity 매핑 |
| `UserCredentialRepository` | 크리덴셜 저장/조회/삭제 |

### 크리덴셜 CRUD (커스텀 구현 필요)

```
GET    /api/webauthn/credentials           # 내 크리덴셜 목록 조회
PATCH  /api/webauthn/credentials/{id}      # 닉네임 수정
DELETE /api/webauthn/credentials/{id}      # 크리덴셜 삭제
```

**삭제 정책**: 마지막 남은 크리덴셜은 삭제 불가 (409 Conflict 반환).

### WebAuthn 설정값

```
rpId: example.com
rpName: Example Enterprise
allowedOrigins: https://example.com
userVerification: required          # 생체인증 필수
authenticatorAttachment: platform   # 디바이스 내장 인증기 우선
attestation: none                   # 기업 내부 서비스이므로 attestation 검증 생략
```

### 크리덴셜 저장 엔티티 (webauthn_credentials 테이블)

| 컬럼 | 타입 | 설명 |
|---|---|---|
| `credential_id` | VARCHAR(1024) PK | Base64url 인코딩 |
| `user_id` | VARCHAR | Base64url 인코딩 |
| `public_key_cose` | BLOB | CBOR 직렬화 공개 키 |
| `sign_count` | BIGINT | 재사용 공격 감지용 |
| `transports` | VARCHAR | 콤마 구분 (usb, nfc, internal 등) |
| `uv_initialized` | BOOLEAN | User Verification 초기화 여부 |
| `backup_eligible` | BOOLEAN | 패스키 동기화 가능 여부 |
| `backup_state` | BOOLEAN | 현재 동기화 상태 |
| `nickname` | VARCHAR | 사용자 정의 기기 이름 |
| `created_at` | TIMESTAMP | 등록 시각 |
| `last_used_at` | TIMESTAMP | 마지막 사용 시각 (인증 성공 시 갱신) |

---

## JWT 토큰 설계

### Access Token

- **서명 알고리즘**: RS256
- **유효 시간**: 15분
- **저장 위치**: JavaScript 메모리 (sessionStorage 허용)

**필수 Claims**:

```json
{
  "iss": "https://auth.example.com",
  "sub": "<user-uuid>",
  "aud": ["https://api.example.com"],
  "exp": "<now + 900>",
  "iat": "<now>",
  "nbf": "<now>",
  "jti": "<unique-id>",
  "kid": "<현재 활성 키 ID>",
  "scope": "openid profile email",
  "roles": ["..."],
  "email": "..."
}
```

### Refresh Token

- **형태**: Opaque Token (JWT 아님)
- **유효 시간**: 7일
- **저장 위치**: httpOnly + Secure + SameSite=Strict 쿠키
- **서버 저장소**: Redis (TTL = 7일)
- **Rotation**: 갱신 시 구 토큰 즉시 무효화, 신규 토큰 발급
- **재사용 감지**: 무효화된 토큰 재사용 시 해당 사용자 전체 세션 폐기

---

## RSA 키 관리 및 JWKS 배포

### 키 관리

- **알고리즘**: RS256 (RSA-2048 이상)
- **Private Key**: AWS KMS 보관 — 서버 밖으로 절대 반출 금지
- **각 키에 `kid` 부여** — 로테이션 시 여러 키 공존 가능

### JWKS 배포 구조

```
Authorization Server (KMS)
    └── Public Key 추출
            └── jwks.json 생성
                    └── S3 (auth-jwks-prod / .well-known/jwks.json)
                                └── CloudFront (keys.example.com)
                                            └── Resource Server (1시간 캐시)
```

**JWKS JSON 구조** (RFC 7517):
```json
{
  "keys": [
    {
      "kty": "RSA",
      "use": "sig",
      "alg": "RS256",
      "kid": "2026-06-key-01",
      "n": "<modulus, base64url>",
      "e": "AQAB"
    }
  ]
}
```

> `d`, `p`, `q`, `dp`, `dq`, `qi` (Private Key 파라미터)는 절대 포함하지 않는다.

### 키 로테이션 (무중단)

```
1. 신규 키 생성 → jwks.json에 추가 (기존 키 유지) → S3 업로드 → CloudFront Invalidation
2. Authorization Server가 신규 kid로 서명 전환
3. 구 토큰 만료(15분) 후 → 구 키 제거 → S3 업로드 → CloudFront Invalidation
```

### S3 + CloudFront 설정

- S3 퍼블릭 액세스 차단 (CloudFront OAC로만 접근)
- CloudFront Cache-Control: max-age=3600
- HTTPS Only
- WAF 연동 (DDoS 방어)

### Resource Server JWT 검증 순서

```
1. Authorization: Bearer <token> 헤더 파싱
2. JWT 헤더에서 kid 추출
3. 로컬 JWKS 캐시에서 kid 검색 (없으면 CloudFront 재조회)
4. RSA 공개 키로 서명 검증
5. Claims 검증: exp, iss, aud, nbf
6. 검증 실패 → 401 Unauthorized
```

---

## SecurityFilterChain 구성 방향

```java
http
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login", "/webauthn/**", "/oauth2/**").permitAll()
        .anyRequest().authenticated()
    )
    .webAuthn(passkeys -> passkeys          // WebAuthn 엔드포인트 자동 생성
        .rpName("Example Enterprise")
        .rpId("example.com")
        .allowedOrigins("https://example.com")
    )
    // QR 로그인은 별도 커스텀 필터 또는 컨트롤러로 처리
    // formLogin은 비활성화 (웹 ID/PW 입력 금지)
    .formLogin(AbstractHttpConfigurer::disable)
    .httpBasic(AbstractHttpConfigurer::disable);
```

---

## 미결 문제 (구현 전 결정 필요)

| ID | 문제 | 결정 필요 사항 |
|---|---|---|
| P1 | Redis 장애 시 Refresh Token 전체 소실 | Redis Cluster 구성 또는 DB 이중화 |
| P2 | 모바일 앱 미설치 사용자 로그인 불가 | Fallback 수단 (SMS OTP, 관리자 임시 코드) |
| P3 | Refresh Token Rotation 동시 요청 Race Condition | Redis 분산 락 또는 Grace Period 도입 |
| P4 | `last_used_at` 갱신 시점 | `AuthenticationSuccessEvent` 리스너에서 처리 |
| P5 | 멀티 테넌트 확장 여부 | Tenant별 kid 네임스페이스 전략 결정 필요 |

---

## 참고 문서

- `resources/엔터프라이즈 로그인 프로세스.md` — 전체 아키텍처 상세 설계
- RFC 8628: https://www.rfc-editor.org/rfc/rfc8628.html
- RFC 6749: https://www.rfc-editor.org/rfc/rfc6749.html
- RFC 7519: https://www.rfc-editor.org/rfc/rfc7519.html
- RFC 7517: https://www.rfc-editor.org/rfc/rfc7517.html
