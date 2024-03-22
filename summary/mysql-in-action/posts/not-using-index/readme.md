# 인덱스를 사용하지 못하는 경우
사전에 first_name, last_name, birh_date 순서로 복합 인덱스가 생성된 것을 가정합니다.

## 1. like 명령어에서 %를 검색어 앞에 사용하는 경우
> "%검색어"와 같이 like 명령어를 구성하는 경우

```
select * from employees where first_name like "%b";

-> Filter: (employees.first_name like '%b')  (cost=30179 rows=33271) (actual time=0.437..120 rows=2008 loops=1)
    -> Table scan on employees  (cost=30179 rows=299468) (actual time=0.0816..94.5 rows=300024 loops=1)
```
복합 인덱스의 첫번째 컬럼으로 first_name이 있지만 실행 계획을 보면 인덱스를 전형 사용하지 못하는 것을 확인할 수 있습니다.

반대로 like 명령어 뒤에 %를 사용하면 어떻게 될까요?

```
select * from employees where first_name like "b%";

-> Index range scan on employees using idx_first_last_name over ('b' <= first_name <= 'b????????????????????????????????????????????????????'), with index condition: (employees.first_name like 'b%')  (cost=11796 rows=26212) (actual time=0.0572..43.7 rows=13333 loops=1)
```

보시는 것처럼 사전에 정의한 복합 인덱스를 활용하여 빠르게 검색하는 것을 알 수 있습니다.

## 2. 복합 인덱스에 정의된 첫번째 컬럼으로 조건을 조합하지 않은 경우
> 인덱스 첫번째 컬럼을 조건에 포함하지 않는 경우

복합 인덱스의 경우 첫번째 컬럼을 조건에 포함하지 않으면 다른 컬럼들이 인덱스가 걸려있다고 하더라도 인덱스를 사용하지 못합니다.
```
select * from employees	where last_name like "B%" and birth_date like "1953%";

-> Filter: ((employees.last_name like 'B%') and (employees.birth_date like '1953%'))  (cost=30179 rows=3696) (actual time=0.171..113 rows=2117 loops=1)
    -> Table scan on employees  (cost=30179 rows=299468) (actual time=0.138..89.2 rows=300024 loops=1)
```
위 쿼리의 경우 두번째 컬럼인 last_name부터 검색 조건을 포함시켰는데요. 실행 계획에서 확인할 수 있듯이 인덱스를 사용하지 못합니다.

```
select * from employees	where first_name like "B%" and birth_date like "1953%";

-> Index range scan on employees using idx_first_last_name over ('B' <= first_name <= 'B????????????????????????????????????????????????????'), with index condition: ((employees.first_name like 'B%') and (employees.birth_date like '1953%'))  (cost=11796 rows=26212) (actual time=0.0558..14.1 rows=969 loops=1)
```
위 쿼리의 경우 첫번째 컬럼부터 검색 조건을 설정해주었는데요. 실행 계획에서도 볼 수 있듯이 인덱스 range scan을 사용하여 검색을 하는 것을 알 수 있습니다.

## 3. 부정형을 사용하는 경우
> 부정형 `<>`, `!=`, `not`을 사용하는 경우

검색 결과를 빠르게 찾기 위해 사용하는 인덱스는 부정형에 대해서 검색하는 것은 쉽지 않은데요. 간단하게 "인덱스를 사용하여 매칭되는 결과를 빠르게 찾고, 그거를 제외한 결과를 반환하면 되는거 아니야?" 라는 생각을 할 수 있지만 검색 후 제외한 값을 반환하는 것과 처음부터 끝까지 확인한 후에 결과에 해당하지 않는 것 중에 어떤 것이 더 효율적이지 바로 판단할 수 없습니다. ex. 인덱스에 매칭되는 값이 9할 이상이라면 인덱스를 사용하지 않은 경우가 휠씬 검색이 빠르다.

아래 쿼리를 통해 확인해보겠습니다. 성이 B로 시작하지 않은 경우 검색입니다.
```
select * from employees where first_name not like "B%";    

-> Filter: (not((employees.first_name like 'B%')))  (cost=30179 rows=266197) (actual time=0.144..118 rows=286691 loops=1)
    -> Table scan on employees  (cost=30179 rows=299468) (actual time=0.137..86.8 rows=300024 loops=1)
```

실행계획에서 볼 수 있듯이 테이블 full scan을 하는 것을 알 수 있습니다.

다음은 반대로 성이 B로 시작하는 경우 입니다.
```
select * from employees where first_name like "B%";

-> Index range scan on employees using idx_first_last_name over ('B' <= first_name <= 'B????????????????????????????????????????????????????'), with index condition: (employees.first_name like 'B%')  (cost=11796 rows=26212) (actual time=0.0597..41.8 rows=13333 loops=1)
```

실행계획에서 볼 수 있듯이 복합 인덱스를 사용하여 검색하는 것을 알 수 있습니다.

