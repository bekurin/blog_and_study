# right, like 명령어 성능 분석

> 예시 데이터 10000개의 전화 번호 저장
> 전화 번호 index 생성 후 몇초 걸리는지 비교
> user 테이블에는 id, phone, created_at, updated_at가 저장되어 있다.

## like 명령어를 사용하여 endsWith 구현
```
explain analyze select * from user where phone like "%6611";

'-> Filter: (`user`.phone like \'%6611\')  (cost=0.45 rows=1) (actual time=0.174..10.2 rows=2 loops=1)
    -> Table scan on user  (cost=0.45 rows=2) (actual time=0.0844..7.18 rows=10000 loops=1)\n'
```

## right 명령어를 사용하여 endsWith 구현
```
explain analyze select * from user where right(phone, 4) = "6611";

-> Filter: (right(`user`.phone,4) = '6611')  (cost=0.45 rows=2) (actual time=0.135..10.1 rows=2 loops=1)
    -> Table scan on user  (cost=0.45 rows=2) (actual time=0.0402..7.49 rows=10000 loops=1)
```

like, right를 사용하여 구현한 endsWith는 둘 다 index를 사용하지 못한다. 둘 다 full scan을 진행하기 때문에 쿼리 실행 비용도 동일하다.

# 마무리
## phone의 중간을 검색하려면 like를 사용해야한다.
```
explain analyze select * from user where phone like "__4468%";
```
right(str, len)을 입력 받는다. 원본 문자열과 문자열과 문자열의 자릿수를 입력받는 것이다.
그렇다면 중간 4자리를 검색하고 싶다면 어떻게 해야할까?
phone을 8자리로 짜른 후 like 검색을 사용하여 startsWith를 구현해야할 것이다.

startsWith, endsWith 기능을 구현해야하는 것이라면 like보다 직관적으로 이해할 수 있기 때문에 right, left 함수를 사용을 고려할 수 있다.
하지만 중간에 포함되었는지, 3자리 후 4자리 검색(ex. "___%input")과 같은 복잡한 검색은 like를 사용을 고려하는 것이 좋다.
