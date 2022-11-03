## 2장 코틀린  기초

### 함수
---
함수를 최상위 수준에 정의할  수 있다. 
- 클래스 안에 함수를 넣을 필요가 없다.
- 하나의 파일에 함수만 여러 개 정의하는 것도 가능하다.

max 함수 선언 (블록이 본문인 함수)
```kotlin
fun max(a: Int, b: Int): Int {
	return if (a > b) a else b
}
```

여기서 자바와 다른 점이라면 if문의 결과가 바로 함수의 결과로 반환되는 것이다. kotlin in action에서는 이런 방식이 가능한 이유를 if문 statement가 아니라 expression으로 구현됐기 때문이라고 한다.

> - statement: 자신을 둘러싸고 있는 가장 안쪽 블럭의 최상위 요소로 존재하면 아무런 값을 만들어내지 않는다는 차이가 있다.
> - expression: 값을 만들어 내며 다른  식의 하위 요소로 계산에 참여할 수 있다. 
> 
> kotlin in action p.62

max 함수 선언 (식이 본문인 함수)
```kotlin
fun max(a: Int, b: Int): Int = 
	if (a > b) a else b
```

식이 본문이 함수를 사용하면 조금 더 간결하고 함수를 구성할 수 있다.

타입 추론은 컴파일러가 타입을 분석해 프로그래머 대신 프로그램 구성 요소의 타입을 정해주는 기능이다. 코틀린 타입추론을 사용하면 식이 본문이 함수의 반환값을 제거할 수 있다.

max 함수 선언 (식이 본문인 함수 with 타입 추론)
```kotlin
fun max(a: Int, b: Int) = 
	if (a > b) a else b
```

이때 유의해야할 점은 식이 본문인 함수에 한해서만 컴파일러가 타입 추론을 진행한다는 것이다. 블록이 본문인 함수에서 반환 타입을 지정하지 않으면 컴파일 에러가 발생한다.

🥕 협업의 관점에서 보았을 때 타입 추론은 개발자가 코드를 해석하는 시간을 많이 사용하게 만들 수 있다고 생각한다. 따라서 식이 본문인 함수에도 타입을 명시하는 것이 좋을 것 같다.

### 변수
---
변수 선언 시 사용하는 키워드는 다음과 같다.

> - val(값을 뜻하는 Value에서 따옴) - 변경 불가능한 참조를 저장하는 변수다.
> - var(변수를 뜻하는 Variable에서 따옴) - 변경 가능한 참조다.
> 
> kotlin in action p.65

var는 변경이 가능하기 때문에 상태 값 관리를 할 때에 이점이 많아 보인다. 하지만 var는 사용하면 사용할 수록 프로젝트가 관리하기 어려워진다. 프로그래머의 실수로 변경되면 안되는 값이 변경될 수도 있고, 비동기 처리 간에 순서가 잘못되어 예상하지 못한 값이 반환될 수도 있다.

따라서 기본적으로 모든 변수를 val로 선언하여 사용하다가 변경이 필요한 부분이 있다면 충분한 검토 후에 var로 변경하는 것이 좋다고 생각된다.

변수 선언(일반, 타입추론)
```kotlin
val age = 25
val age: Int = 25
```

변수 또한 타입 추론을 지원한다. 하지만 가독성과 다른 프로그래머와 협업을 생각하면 타입은 항상 명시하는 것이 좋다고 생각된다.

코틀린은 또한 문자열 템플릿 기능을 제공한다.
helloName(문자열 템플릿)
```kotlin
fun helloName(name: String) {
	println("Hello, $name!")
}

>> helloName("hangman")
>> Hello, hangman!
```

코드에서 확인할 수 있지만 문자열 템플릿은 문자열 생성에 있어 엄청난 편의를 제공해준다. 기존에 자바로 해당 함수를 구현하려면 "Hello, " + name + "!" 과 같이 +와 ""를 많이 사용해야했다.

java
```java
void helloName(String name) {
	System.out.println("Hello, " + name + "!");
}

>> helloName("hangman")
>> Hello, hangman!
```

🥕 문자열 템플릿을 사용하게 되면 shift를 더 오랫동안 쓸 수 있을 것이다. 

### 클래스
---
같은 기능을 하는 클래스를 자바와 코틀린을 사용하여 구현해보겠다.

```java
public class Person {
	private final String name;

	public Person(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
```

Person 클래스에 생성자와 변수 name에 대한 getter 함수가 구현되어 있다. 헤당 클래스를 코틀린으로 변경하면 다음과 같다.

```kotlin
class Person(
	val name: String
)
```

1줄로 표현할 수 있지만 변수 목록을 빠르게 식별하기 위해 줄바꿈을 해서 3줄로 늘었다. 완벽하게 같은 기능을 하는 클래스라면 생성자와 getter는 어디에 정의되어 있을까?

코틀린에서 클래스 선언 시 () 안에 들어있는 값들에 대해서 기본 생성자를 제공해준다. 또한 변수 키워드 val, var에 따라서 val는 getter, var는 getter와 setter를 만들어 준다.

🥕 참조에 따라 getter 또는 setter를 만들어주는 기능은 변수의 목적을 휠씬 잘 표현하는 것 같다.

### 프로퍼티
---
프로퍼티는 필드와 접근자를 한데 묶는 개념을 말한다.
- val: 읽기 전용 프로퍼티로, 코틀린 컴파일러는 필드와 getter를 만든다.
- var: 읽기 쓰기 전용 프로퍼티로, 코틀린 컴파일러는 필드와 getter, setter를 만든다.

다음은 읽기 전용 프로퍼티 name과 age를 가져오는 방법에 있어 코틀린과 자바의 차이를 보인 것이다.

읽기 전용 프로퍼티 (java)
```java
public class Person {
	private final String name;
	private final int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
}

>> Person person = new Person("hangman", 99)
>> System.out.println(person.getName())
>> hangman
```

읽기 전용 프로퍼티 (kotlin)
~~~kotlin
class Person(
	val name: String,
	val age: Int
)

>> person: Person = Person("hagnman", 99)
>> println(person.name)
>> hangman
~~~

자바는 프로퍼티의 목적에 따라 getter 또는 setter를 만들어줘야한다. 하지만 코틀린에서는 프로퍼티의 목적에 따라 키워드를 달리 해주면 컴파일러가 자동으로 getter 또는 setter를 만들어준다.

프로퍼티를 읽는 과정에서도 차이가 보인다. 자바의 경우 개발자가 작성한 함수를 호출한다. 이에 반해 코틀린의 경우 객체의 필드 이름을 그대로 사용하여 값을 할당하면 setter로 값을 할당하지 않으면 getter로 동작하게 된다.

🥕 자바의 경우 접근자를 private으로 만드는 것을 권장한다. 또한 private 필드에 대해서 getter와 setter를 만들 것을 권장한다. 권장은 필수는 아니라는 것이다. 혹시 필수가 아니기 때문에 만들지 않는다면 코드를 작성할 때에 문제를 직면하여 만들게 될 것이다.

코틀린은 이런 문제를 컴파일러가 해결해준다. val의 경우 필드와 getter를 자동으로 제공하고, var의 경우 필드와 getter, setter를 자동으로 제공해준다.

컴파일러가 자동으로 만들어주는 것으로 자바에서처럼 getter와 setter를 작성하는 수고를 덜고, 개발자의 실수를 막아주었다.

### 커스텀 접근자
---
커스텀 접근자는 코틀린 컴파일러가 만든 getter 또는 setter 말고, 개발자가 변수의 특성에 맞게 새롭게 정의한 것을 말한다.

다음은 kotlin in action의 예제이다.

~~~kotlin
class Rectangle(var height: Int, var width: Int) {
	val isSquare: Boolean
		get() = height == width

	fun isSquareFun(): Boolean =
		height == width
}
~~~

사각형을 정의한 클래스에서 높이와 너비를 입력 받는다. 정사각형인지 알려주는 기능이 새로운 요구사항으로 발생했다. 그렇다면 별도의 필드에 값을 저장할 필요없이 커스텀 접근자를 사용하여 그때그때 너비와 높이가 같은지 계산하여 정사각형 여부를 알 수 있다.

위의 코드는 정사각형 여부를 판단하는 기능을 커스텀 접근자와 함수를 사용하여 구현한 것이다. 다음은 kotlin in action에서 커스텀 접근자를 사용해야할 상황에 대해 기술한 것이다.

> 일반적으로 클래스의 특성(프로퍼티에는 특성이라는 뜻이 있다)을 정의하고 싶다면 프로퍼티로 그 특성을 정의해야한다.
> kotlin in action p.74

🥕 코틀린의 커스텀 접근자는 그때그때 값을 계산하기 때문에 클라이언트가 프로퍼티에 접근할 때마다 getter가 프로퍼티 값을 매번 다시 계산한다. 요구사항 중에 주기적으로 값이 바꿔져 상태를 확인해야하는 것이 있다면 사용을 생각해보면 좋을 것 같다.

### 선택 표현과 처리: enum과 when
---
enum은 열거형으로 타입이나 상태를 저장할 때 많이 사용한다. String이나 Int 등의 타입으로 상태를 저장하면 분명 실수 발생하기 마련이기 때문이다. 타입과 상태에 대한 예는 다음과 같다.
- npc의 타입이 일반타입(normal), 상인(merchant), 퀘스트(quest) 등으로 나눠져있다면 npc의 상태를 저장하기 위해 enum을 사용한다.
- 배달 주문을 하는 과정에 있어 결제 완려 후 대기 상태(ready), 사장님이 주문 확인 상태 (accept), 배달 상태 (delivery), 배달 완료 상태(complete)가 있다면 상태를 확인하기 위해 enum을 사용한다.

다음은 배달 주문 과정을 enum 클래스로 정의한 것이다.
~~~ kotlin
enum class OrderState {
	READY, ACCEPT, DELIVERY, COMPLETE
}
~~~

다음은 주문 상태에 따라 행동을 다르게 하는 기능을 when절을 사용하여 구현한 것이다.
```kotlin
fun doSomethingBy(state: OrderState) {
	when(state) {
		state.READY -> "배달 준비 기능 실행!"
		state.ACCEPT -> "주문 확인 기능 실행!"
		state.DELIVERY -> "배달 기능 실행!"
		state.COMPLETE -> "배달 완료 기능 실행!"
	}
}
```

🥕 지금의 enum 클래스는 단순히 정보를 열거하는 것을 넘어 enum 클래스 안에 프로퍼티 또는 함수를 정의할 수 있다. 