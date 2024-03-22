# 처리율 제한 알고리즘 구현 (sliding window rate limiter)

## 사용 명령어
- zadd <key> <score> <value>
- zrange <key> <start> <end>
- zremrangebyscore <key> <min> <max>

## 구현 내용
5분에 3개의 요청만 가능하도록 구현

1. 요청이 들어오면 현재 시간 기준으로 5분 전까지의 값 제거
```
zremrangebyscore user:1000 0 1694887200000
```
2. key에 저장된 모든 원소 검색
```
127.0.0.1:6379> zrange user:1000 0 -1
1) "uuid1"
2) "uuid2"
3) "uuid3"
```
3. 2의 결과가 3개 이상이라면 요청 거부 (429 status code)
4. 2의 결과가 3개 미만이라면 요청 허용 및 zadd 명령어를 사용하여 값 추가
```
zadd user:1000 1694886636170 uuid4
```

## 마무리
redis의 명령어는 원자성을 보장하기 때문에 race condition을 고려하지 않아도 됩니다.
(때문에 처리율 제한 알고리즘 구현만 고민해도 됩니다)
하지만 Sorted Set을 사용한 처리율 제한 알고리즘은 모든 사용자에 대해서 만들어줘야하기 때문에 일정 수치 이상으로 규모가 커지면 메모리 관리 효율성을 고려해봐야할 수 있습니다.
