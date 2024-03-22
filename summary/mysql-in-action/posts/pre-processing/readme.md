# 선처리가 필요한 함수를 사용할 때 유의해야한다.

기존에 인덱스를 사용하는 쿼리더라도 선처리 함수들 ex. substr, length, left, right 함수를 사용화면 인덱스를 사용하지 못할 수 있습니다.
인덱스 기준 컬럼의 원본 값을 처리한 후에 검색을 진행하는 것이기 때문에 어찌보면 당연하게 생각할 수 있는데요.

쿼리로 확인해보겠습니다.
사전에 phone 컬럼을 사용하여 인덱스를 구성했습니다.

## like 검색에서 __을 사용하는 경우

```
select * from user where phone like "__8%";

-> Filter: (`user`.phone like '__8%')  (cost=2.35 rows=2) (actual time=0.169..8.739 rows=1091 loops=1)
    -> Table scan on user  (cost=2.35 rows=21) (actual time=0.140..6.923 rows=10000 loops=1)
```

실행 계획을 통해 알 수 있듯 인덱스를 사용하지 못한고, 테이블 full scan을 진행하는 것을 알 수 있습니다.
like 명령어를 사용할 때 _ 표시는 검색 대상이 되는 문자열의 자릿수를 1자리 건너뛰겠다는 의미입니다.

따라서 위 쿼리는 모든 phone 컬럼의 값에서 2자리를 생략한 후에 8로 시작하는 것을 찾기 때문에 인덱스를 사용할 수 없습니다.

## substr 함수를 사용하는 경우
```
select * from user where substr(phone, 3, 1) = "8";

-> Filter: (substr(`user`.phone,3,1) = '8')  (cost=2.35 rows=21) (actual time=0.050..7.637 rows=1091 loops=1)
    -> Table scan on user  (cost=2.35 rows=21) (actual time=0.041..5.440 rows=10000 loops=1)
```

like 검색과 같은 기능을 하는 쿼리입니다. phone 컬럼의 3번째 자릿수에 위치한 문자값을 가져와서 8인지 비교합니다.

실행 계획을 봐도 like와 마찬가지로 인덱스를 사용하지 못하는 것을 알 수 있습니다.


## length 함수를 사용하는 경우

```
select * from user where length(id) = 2;

-> Filter: (length(`user`.id) = 2)  (cost=2.35 rows=21) (actual time=0.045..2.039 rows=90 loops=1)
    -> Table scan on user  (cost=2.35 rows=21) (actual time=0.041..1.616 rows=10000 loops=1)
```

위 쿼리는 pk가 10~99인 user를 검색하는 쿼리입니다.

id는 primary key이지만 length 함수를 사용하여 처리를 하니 인덱스를 활용하지 못하는 것을 알 수 있습니다.


## 마무리
검색 조건에 따라 인덱스를 사용하는지는 많이 확인하지만 내장 함수가 인덱스에 어떤 영향을 주는지는 많이 고민해보지 않았던 거 같습니다.

보통의 내장 함수들은 컬럼의 값을 처리하기 위해 사용되는데요. 쿼리의 검색 범위가 크다면 내장함수를 사용하여 인덱스를 못 사용하게 하고 있지는 않은지 생각해보면 좋을 것 같습니다.
