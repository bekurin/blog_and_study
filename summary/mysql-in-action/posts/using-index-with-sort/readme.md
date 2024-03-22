# order by 절에서 인덱스를 구성하는 방법

## 인덱스를 사용하지 못하는 경우
```
select birth_date, first_name from employees order by birth_date;

-> Sort: employees.birth_date  (cost=30179 rows=299468) (actual time=161..173 rows=300024 loops=1)
    -> Table scan on employees  (cost=30179 rows=299468) (actual time=0.0821..67.5 rows=300024 loops=1)
```
인덱스를 정의하지 않은 상태에서 위 쿼리는 모든 테이블을 스캔하여 값을 출력합니다.

## 인덱스를 사용하는 경우

birth_date, first_name을 기준으로 인덱스(idx01)을 생성했습니다.

```
select birth_date, first_name from employees order by birth_date;

-> Covering index scan on employees using idx01  (cost=30179 rows=299468) (actual time=0.112..77.9 rows=300024 loops=1)
```
첫번째 쿼리와 완전히 같지만 실행 계획을 보면 covering index를 활용하고 있다는 것을 알 수 있습니다.

## 마무리
검색, 정렬, 집계를 실행할 때 인덱스를 사용하고 싶다면 인덱스의 첫번째 컬럼을 적극 활용해야합니다.
