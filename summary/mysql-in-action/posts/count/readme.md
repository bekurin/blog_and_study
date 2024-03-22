# count 쿼리 사용법

*(와일드카드) 연산을 사용하면 테이블의 전체열 정보를 상관하지 않고, 행을 계산한다.

## 여러 속성에 대한 집계가 필요하다면 아래 쿼리를 고려해볼 수 있다.
```
select sum(dept_no='d005'), sum(dept_no='d004') from dept_emp;

-> Aggregate: sum((dept_emp.dept_no = 'd005')), sum((dept_emp.dept_no = 'd004'))  (cost=66413 rows=1) (actual time=112..112 rows=1 loops=1)
    -> Covering index scan on dept_emp using dept_no  (cost=33299 rows=331143) (actual time=0.0548..46.3 rows=331603 loops=1)

```
dept_no가 d005, d004인 경우를 집계한다.


```
select count(dept_no='d005' or null), count(dept_no='d004' or null) from dept_emp;

-> Aggregate: count(((dept_emp.dept_no = 'd005') or (0 <> NULL))), count(((dept_emp.dept_no = 'd004') or (0 <> NULL)))  (cost=66413 rows=1) (actual time=123..123 rows=1 loops=1)
    -> Covering index scan on dept_emp using dept_no  (cost=33299 rows=331143) (actual time=0.0456..46.5 rows=331603 loops=1)
```
dept_no가 d005 or null, d004 or null인 경우를 집계한다.
예시 데이터에서는 dept_no가 null인 경우가 없어 scan 하는 범위가 같다.
