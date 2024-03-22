## Strings 데이터 타입

Strings는 문자열, 직렬화된 객체 (Ex. json), 이진 배열 등을 저장할 수 있습니다.


![MSET-example.png](images%2FMSET-example.png)

M(ulti)SET의 줄임말로 key value key value 형식으로 여러 개의 Strings 데이터 타입을 저장할 수 있습니다.

![INCR.png](images%2FINCR.png)

문자열을 저장하지만 값이 숫자인 경우 숫자 처럼 계산을 할 수 있습니다.
- INCR: key에 해당하는 값 1 증가
- INCRBY: 설정한 increment 값만큼 값 증가

![serialized-object.png](images%2Fserialized-object.png)

앞서 설명한 것과 동일하게 직렬화 된 객체도 저장이 가능합니다. 이를 통해 json serializer, deserializer 만 있다면 redis를 통해 객체를 읽고 쓰는 것이 가능합니다.

## 사용 사례
- HTML, URL, API 응답, 데이터베이스 정보, 실시간 위치/날씨/주식 정보 등 캐싱 구현
- OTP 생성, 분산 락 구현
- 처리율 제한기 (Fixed Window Rate Limiter)
