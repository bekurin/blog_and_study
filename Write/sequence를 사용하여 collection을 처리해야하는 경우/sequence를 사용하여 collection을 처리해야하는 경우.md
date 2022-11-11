대규모 collection의 처리에서 sequence를 사용하면 좋다는 이야기는 한번쯤 들어보았을 것이다. 하지만 정확히 어느 정도의 크기의 collection을 처리할 때를 말하는 것이며 어떤 경우에 sequence가 보다 더 좋은 성능을 낼지 알고 싶어서 몇가지 경우에 대해 확인해보겠다.

1. collection의 크기가 큰 경우, 작은 경우 
2. 연산 과정이 복잡한 경우, 복잡하지 않은 경우
3. 연산하는 과정에 종료 조건이 있는 경우, 종료 조건이 없는 경우

위 가정들에 대해서 collection으로 계산했을 때와 sequence로 계산했을 때 차이를 알아보며 어떤 경우에 sequence를 고려해야하는지 알아보겠다.

### Lazy와 Eager 연산의 차이
연산을 하기 앞서 sequence와 collection의 연산 과정의 차이를 이해할 필요가 있었다. collection의 연산은 eager하고 sequence의 연산은 lazy한다고 표현한다. 이 둘의 차이를 코드와 그림을 통해 알아보겠다.

![[sketch1668132231532.png]]

eager 방식은 각 단계에서 모든 원소에 대한 계산을 완료한 후에 다음 단계로 넘어가는 방식으로 계산한다.
lazy 방식은 원소마다 모든 단계에 대해 연산을 한 후에 다음 원소로 넘어가는 방식으로 계산한다.

~~~kotlin
(1..4).asSequence()  
    .map { print("M$it "); it * it }  
    .filter { print("F$it "); it % 2 == 0 }  
    .first()

(1..4)
    .map { print("M$it "); it * it }  
    .filter { print("F$it "); it % 2 == 0 }  
    .first()

// sequence(lazy): M1 F1 M2 F4             
// collection(eager): M1 M2 M3 M4 F1 F4 F9 F16
~~~

코드로 보면 조금 더 명확하게 할 수 있다. collection과 sequence 모두 map -> filter -> first 단계로 구성되어 있다. 주석을 보면 sequence의 경우 1, 2 원소에 대해서만 연산을 완료하고 결과를 도출했지만 collection의 경우 map과 filter는 모든 원소들에 대해서 연산을 한 것을 알 수 있다.

### 테스트
#### collection의 크기가 큰 경우, 작은 경우
collection의 크기가 크다면 sequence를 고려하면 좋다는 것은 알고 있었지만 크기가 크다는 기준이 모호하여 소수를 계산하는 연산을 기준으로 연산 시간을 비교해보았다.

~~~kotlin
// count = 700_000
fun primeSequence(count: Int) =  
    generateSequence(2, ::nextPrime)  
        .take(count)  
        .toList()  
  
fun primeCollection(last: Int): List<Int> =  
    (2 until last + 1)  
        .filter { it.isPrime() }  
        .toList()

// primeSequenceMs = 1933
// primeCollectionMs = 1937
~~~

primeSequence는 count 만큼의 소수를 계산하여 반환한다. primeCollection은 2부터 last + 1까지 숫자에 대해 소수인 경우를 계산한다. 따라서 primeSequence를 먼저 계산한 후에 primeSequence의 마지막 값을 primeCollection에 넣어주는 것으로 둘의 결과를 맞췄다.

위 코드는 count가 70만일 때 즉, 소수를 70만개 계산하는 시간을 측정한다.

70만개 정도의 소수를 계산해야 sequence가 4ms 더 빠르다. 이것보다 collection의 크기가 작다면 collection으로 연산하는 것이 더 빠르다.

#### 연산 과정이 복잡한 경우, 복잡하지 않은 경우
step by step으로 연산을 하는 eager는 연산의 단계마다 결과 collection을 만들어 element by element로 연산을 하는 lazy에 비해 메모리를 많이 사용한다. 

~~~kotlin
val numbers: List<Int> = (0 until 300_000_000).toList()  
numbers  
    .map { it * 1 }  
    .filter { it % 2 == 0 }  
    .filter { it % 4 == 0 }  
    .filter { it % 6 == 0 }  
    .map { it + (it / 2 + 1) }  
    .filter { it % 7 == 0 }  
    .toList()  
~~~

위의 코드는 맥북 m1 pro 16인치에서 돌렸을 경우 OutOfMemoryError가 발생한다.
> Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

같은 코드를 sequence로 바꾸면 어떻게 될까?
~~~kotlin
numbers.asSequence()  
    .map { it * 1 }  
    .filter { it % 2 == 0 }  
    .filter { it % 4 == 0 }  
    .filter { it % 6 == 0 }  
    .map { it + (it / 2 + 1) }  
    .filter { it % 7 == 0 }  
    .toList()  
~~~
asSequence 함수를 통해 sequence로 바꿔 코드를 실행하면 아무런 Error 없이 정상적으로 코드가 동작하는 것을 알 수 있다.

따라서 연산의 과정이 복잡하다면 각 단계마다 결과 collection을 생성하는 eager 연산 보다는 element by element로 연산하는 lazy 연산을 고려하는 것이 좋다.

#### 연산하는 과정에 종료 조건이 있는 경우, 종료 조건이 없는 경우
종료 조건이라는 것은 연산 과정에서 해당 조건을 만족하면 더 이상의 연산을 진행하지 않는 것을 말한다.

~~~kotlin
fun firstCollection(last: Int) =  
    (1 until last)
	    .map { it * 2 }  
        .first { it % 7919 == 0 }  
  
fun firstSequence(last: Int) =  
    (1 until last).asSequence()  
        .map { it * 2 }  
        .first { it % 7919 == 0 }

// firstSequenceMs = 2
// firstCollectionMs = 13
~~~

1부터 last까지의 숫자 중에 2배를 하고 7919로 나머지 연산을 했을 때 나누어 떨어지는 첫번째 숫자를 구하는 연산이다.

결과에서도 알 수 있지만 종료 조건이 있는 경우 sequence가 휠씬 빠르다. lazy와 eager 연산을 이해했다면 예상 가능한 결과이다. 

firstCollection의 경우 first값을 도출하기 위해 1부터 last까지의 모든 수에 대해 2배를 하는 map 연산을 모두 수행한 후에 first 값을 찾기 때문이다.

### 마무리
eager 연산의 장단점
- 장점: step by step으로 연산을 진행한다. 따라서 모든 원소에 어떤 처리가 필요한 경우 대체로 sequence에 비해 빠르다.
- 단점: 각 단계마다 결과 collection을 만들어 sequence에 비해 메모리가 많이 필요하다. 연산 중간에 종료해야할 필요가 있을 경우 불필요한 연산을 할 수 있다.
lazy 연산의 장단점
- 장점: 연산 과정에 first(), take()와 같이 종료 조건이 필요하다면 대체로 eager연산에 비해 좋은 성능을 낸다. 결과를 얻기 까지 연산 횟수나 종료 조건을 알 수 없다면 무한 sequence를 만들어서 연산할 수 있다.
- 단점: element by element로 연산을 하기 때문에 각 단계마다 collection을 만들지 않아 연산 단계 중간에 sort, sum, max와 같은 연산을 할 수 없다.

sequence 연산은 종료 조건이 필요한 경우, 연산의 단계가 많은 경우, collection의 크기가 70만 이상이 되는 경우에 고려해보면 좋을 것 같다.