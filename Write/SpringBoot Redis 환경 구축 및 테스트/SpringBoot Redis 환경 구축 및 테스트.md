### springBoot redis 설정

step 1 ->
```
brew install redis
```

```
redis-server
```

```
redis-cli
```

- spring application 실행

RedisConfig ->
- 코드 설명
- Serializer 3종류 설명?

문제점
1. 다른 사람에게 공유할 때 환경 설정이 사전에 필요하다는 것을 알려야한다.
2. 로컬에서 앱 실행을 위해 매번 redis-server 명령어를 입력해야한다.
해결
1. embedded redis를 사용하여 runtime 시에만 동작하는 redis 서버 구축

### embedded redis 설정
step 2 ->
- embedded redis 설정 build.gralde.kts
	- issue: slf4j: multiple binding error
- EmbeddedRedisConfig 설정
---

### redis 코드 테스트
npcService ->
- 코드 설명

npcServiceTest ->
- 코드 설명

```
./gradlew clean build
```
- 여러 spring context 실행 시에 포트 사용으로 인한 build Error <- jojoldu 블로그 링크

### 마무리
- redis 부하테스트 툴 사용하여 성능 측정

---
추후 redis 캐시 성능 비교에서 다룬다.
조회 함수 캐싱
- h2 database 연결 후
	- caching 전 후 성능 확인 (CRUD)
	- 예상 1: 조회에서 성능 차이는 확실하지만 생성, 수정, 삭제에는 성능 최적화가 될 수 없다.

