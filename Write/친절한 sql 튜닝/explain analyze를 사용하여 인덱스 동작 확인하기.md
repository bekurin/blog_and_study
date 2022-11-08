### 인덱스 동작 원리

인덱스의 동작 원리는 다음 질문들을 통해 이해할 수 있다.

1.  D로 시작하는 앱 찾기
2.  e로 끝나는 앱 찾기
3.  중간에 a가 들어가는 앱 찾기

![](https://blog.kakaocdn.net/dn/cE4RFo/btrQEp9zAFs/8VT8UGsy0daRLNsrdKN8Sk/img.png)

1번은 효과적인 인덱스 사용의 예시이다.  
Developer부터 시작하여 FaceTime까지 확인한 후에 더 이상의 결과가 없다고 판단할 수 있기 때문이다.

2번과 3번은 효과적이지 않은 인덱스 사용 예시이다.  
2번과 3번 결과를 찾기 위해서는 처음부터 끝까지 앱을 찾아봐야 하기 때문이다.

특히 3번은 아이템의 중간중간마다 값이 존재하는지 확인해야 하므로 제일 비효율적이게 된다.

### 인덱스 활용 예시

아래 테이블을 기준으로 인덱스 emp\_index01(first\_name, gender, birth\_date)을 만들어 다음에 대해 알아보겠다.

-   인덱스를 사용한 쿼리와 사용하지 않은 쿼리의 성능 차이를 알아본다.
-   인덱스를 효과적으로 사용한다면 order by 절을 생략할 수 있다.

```
mysql> desc employees;
+------------+---------------+------+-----+---------+-------+
| Field      | Type          | Null | Key | Default | Extra |
+------------+---------------+------+-----+---------+-------+
| emp_no     | int           | NO   | PRI | NULL    |       |
| birth_date | date          | NO   |     | NULL    |       |
| first_name | varchar(14)   | NO   | MUL | NULL    |       |
| last_name  | varchar(16)   | NO   |     | NULL    |       |
| gender     | enum('M','F') | NO   |     | NULL    |       |
| hire_date  | date          | NO   |     | NULL    |       |
+------------+---------------+------+-----+---------+-------+
6 rows in set (0.00 sec)
```

employees 테이블의 구조이다. 총 30만 24개의 행이 있다.

#### 인덱스 사용한 퀴리의 성능 차이

다음은 인덱스를 사용하지 않은 검색 쿼리의 결과이다.

```
mysql> explain analyze 
    -> select * from employees 
    -> where first_name = "Fumiya";

| EXPLAIN                                                                                                                                                                                                                            
| -> Filter: (employees.first_name = 'Fumiya')  (cost=30179.05 rows=29947) (actual time=0.968..74.887 rows=212 loops=1)
    -> Table scan on employees  (cost=30179.05 rows=299468) (actual time=0.062..63.080 rows=300024 loops=1)
1 row in set (0.07 sec)
```

Table scan을 사용하여 전체 테이블을 확인한다.

쿼리 비용은 30179, 스캔한 행 수는 29만 9468개로 거의 모든 행을 확인한다. 하지만 결과 행의 수는 212개로 스캔한 행 수에 비해 결과의 행수가 적다는 것을 알 수 있다.

이렇게 전체 테이블의 수에 비해 결과 행의 수가 적다면 인덱스를 효과적으로 적용할 수 있다.

다음은 인덱스를 사용한 쿼리의 결과이다.

```
mysql> explain analyze 
    -> select * from employees 
    -> where first_name = "Fumiya";

| EXPLAIN                                                                                                                                  
| -> Index lookup on employees using emp_index01 (first_name='Fumiya')  (cost=74.20 rows=212) (actual time=1.090..1.558 rows=212 loops=1)
1 row in set (0.00 sec)
```

이전과는 다르게 emp\_index01을 사용하여 index lookup 방식으로 테이블을 확인한다.

쿼리의 비용은 30179에서 74로 내려갔고, 확인한 행의 수 또한 결과 행의 수와 같은 것을 알 수 있다.

#### order by 절 생략

인덱스를 보다 효율적으로 사용하면 쿼리의 order by를 생략할 수 있다. 다음은 인덱스를 사용하지 않은 쿼리이다.

```
mysql> explain analyze 
    -> select * from employees 
    -> where first_name = "Fumiya" 
    -> and gender = "M" 
    -> order by birth_date;

| EXPLAIN                                                                                                                                                                                                                                                                                                                                                                             
| -> Sort: employees.birth_date  (cost=30179.05 rows=299468) (actual time=82.205..82.211 rows=128 loops=1)
    -> Filter: ((employees.gender = 'M') and (employees.first_name = 'Fumiya'))  (cost=30179.05 rows=299468) (actual time=0.645..82.138 rows=128 loops=1)
        -> Table scan on employees  (cost=30179.05 rows=299468) (actual time=0.146..62.501 rows=300024 loops=1)
1 row in set (0.09 sec)
```

29만 9468개의 행을 확인하고, 조건에 맞게 128개의 행만 남겨 birth\_date를 기준으로 정렬한다.

쿼리의 비용은 30179이고, 첫 행의 반환 시간이 82.205ms로 시간과 비용이 많이 든다는 것을 알 수 있다.

다음은 인덱스를 사용하여 order by 절을 생략한 쿼리이다.

```
mysql> explain analyze
    -> select * from employees
    -> where first_name = "Fumiya"
    -> and gender = "M"
    -> order by birth_date;

| EXPLAIN                                                                                                                                                                                              
| -> Index lookup on employees using emp_index01 (first_name='Fumiya', gender='M'), with index condition: (employees.gender = 'M')  (cost=44.80 rows=128) (actual time=0.470..0.606 rows=128 loops=1)
1 row in set (0.01 sec)
```

결과 행과 같은 행인 128개의 행을 확인하고, 정렬 과정 또한 생략됐다.

퀴리의 비용은 44이고, 첫 행의 결과 반환 시간이 0.47ms로 시간과 비용이 많이 줄어든 것을 확인할 수 있다.

#### 결론

효과적인 인덱스 사용법

-   검색 성능 향상 뿐 아니라 order by 절을 생략할 수 있다.
-   테이블의 행의 개수는 많고, 검색 결과의 행 개수가 적을 때 사용한다.
-   퀴리의 결과가 테이블에 적재된 대부분의 행일 경우 Table Full Scan이 인덱스를 사용하는 것보다 속도가 더 빠르다.
-   emp\_index01의 경우 (first\_name), (first\_name, gender), (first\_name, gender, birth\_date)에 대해서 인덱스를 사용할 수 있고, 각 인덱스의 마지막 조건을 범위 조건으로 변경해도 인덱스 적용이 가능하다.