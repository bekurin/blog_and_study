## 3장 함수 정의와 호출

다음은 코틀린의 Collection 객체의 각 원소들을 합쳐 하나의 문자열로 반환하는 함수이다.
~~~kotlin
fun <T> Collection<T>.joinToString(
	separator: String = " ",
	prefix: String = "",
	postfix: String = ""
): String {
	val result = StringBuilder(prefix)
	for((index, element) in this.withIndex()) {
		if (index > 0) result.append(separator)
		result.append(element)
	}
	result,append(postfix)
	return result.toString()
}

>> val item = listOf(1,2,3)
>> item.joinToString()
>> 1 2 3
>> item.joinToString(separator = ", ")
>> 1, 2, 3
>> item.joinToString(separator = ", ", prefix = "{", postfix = "}")
>> {1, 2, 3}
~~~

위 코드를 중심으로 디폴트 파라미터와 확장 함수에 대해 알아보겠다.

### 디폴트 파라미터
---
디폴트 파라미터는 자바의 오버로딩을 편하게 구현할 수 있는 방법으로 함수의 파라미터의 기본값을 설정할 수 있는 것이다. 위 코드에서 확인할 수 있지만 디폴트 파라미터를 사용하면 separator, prefix, postfix 중에 변경하고 싶은 값만 선택하여 변경하는 것이 가능하다.

자바에서는 디폴트 파라미터와 같이 동작하게 하기 위해서는 상당히 많은 오버로딩 함수를 만들어야 하지만 코틀린에서는 단 1개의 함수만을 구현하는 것으로 가능하다.

### 확장 함수
---
확장 함수를 kotlin in action에서는 다음과 같이 정의한다.
> 확장 함수는 어떤 클래스의 멤버 메서드인 것처럼 호출할 수 있지만 그 클래스의 밖에 선언된 함수다.
> kotlin in action p.115

kotlin in action의 정의대로 확장 함수는 클래스 밖에서 정의하기 때문에 클래스 내에 선언된 private, protected 변수에 대해 접근할 수 없다.

확장 함수의 이해하기 위해 다음과 같은 요구사항이 발생했다고 가정하겠다. 
 요구사항: 정수형 collection을 입력 받았을 때 모든 원소의 인덱스와 원소를 출력하는 기능

다음은 요구사항을 구현한 코드이다.
```kotlin
fun Collection<Int>.printAll(): Unit {  
    for ((index, element) in this.withIndex()) {  
        println("Collection<Int>.get($index) = $element")  
    }  
}

>> val list = listOf(1,2,3,4,5)
>> list.printAll()
>> Collection<Int>.get(0) = 1
>> Collection<Int>.get(1) = 2
>> Collection<Int>.get(2) = 3
>> Collection<Int>.get(3) = 4
>> Collection<Int>.get(4) = 5
```

위의 코드는 모든 정수형 collection에 적용된다. 이해하기 위해서는 수신 객체 타입과 수신 객체에 대해 알아야한다. 다음은 2 개념에 대한 설명이다.
- 수신 객체 타입은 printAll()이라는 확장 함수를 적용시킬 범위에 해당한다. 따라서 위 코드와 같이 collection<Int>를 수신 객체 타입을 지정했다면 모든 정수형 collection이 표현된 것이다.
- 수신 객체는 확장 함수에서 호출되는 대상의 값으로 개발자에 의해 결정된다. 위 코드에서 list와 같이 확장 함수를 적용시킬 값을 말한다.

🥕 확장 함수는 외부 라이브러리 사용할 때 고민해보면 좋을 것 같다. 예를 들어 JPA의 findById 함수에서 예외처리를 추가해야한다면 getByIdOrThrow, findByIdOrThrow와 같은 확장 함수를 구현하여 쉽게 요구사항을 해결할 수 있을 것이다.

확장 함수는 외부에서 제공하고 싶은 함수를 만들 수 있는만큼 편리하지만 남용해서는 안된다고 생각한다. 클래스를 관리함에 있어 확장 함수는 분명 큰 편의를 가져오겠지만 필요에 따라 무분별하게 사용한다면 배보다 배꼽이 더 커지는 상황이 발생할 수 있을 것 같다.

간단한 예로 어떤 클래스의 멤버 함수 2개, 확장 함수 5개가[]() 구현되어 있는 상황이 있다.