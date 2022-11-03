## 4장 클래스, 객체, 인터페이스

### 인터페이스
---
코틀린은 자바와 마찬가지로 인터페이스에 추상 함수 뿐 아니라 기본 함수를 만들 수 있지만 상태를 관리할 수는 없다. 다음은 Clickable과 Focusable 인터페이스를 만들고, Button 클래스를 통해 구현하는 코드이다.

```kotlin
interface Clickable {
	fun click()
	fun showOff() = println("I'm clickable!")
}

interface Focusable {
	fun showOff() = println("I'm focusable!")
}

class Button: Clickable, Focusable {
	override fun click() = println("I was clicked!")
	override fun showOff() {
		super<Clickable>.showOff()
		super<Focusable>.showOff()
	}
}

>> val button: Button = Button()
>> button.click()
>> I was clicked!
>> button.showOff()
>> I'm clickable!
>> I'm focusable!
```

Clickable은 추상 함수 click과 기본 함수 showOff를 제공한다. 여기서 기본 함수는 인터페이스에서 기능이 정의된 함수로 구현 클래스에서 구현을 하지 않아도 된다.

자바에서 인터페이스를 확장하기 위해서는 implements 키워드를 사용하지만 코틀린에서는 클래스 이름 뒤에 :을 붙이고 인터페이스 이름을 적으면 된다.

Clickable과 Focusable은 모두 showOff 라는 기본 함수를 제공한다. 이처럼 인터페이스 같은 이름의 함수를 기본 함수로 제공화면 어떤 함수도 실행되지 않고, 컴파일 에러가 발생한다. 따라서 본 함수로 작성한다고 해서 구현을 무조건 하지 않아도 되는 것은 아니다.

### 변경자
---
#### 상속 제어 변경자
> 자바의 클래스와 메서드는 기본적으로 상속에 대해 열려있지만 코틀린의 클래스와 메서드는 기본적으로 final이다.
> kotlin in action p.147

|변경자|설명|
|-|-|
|open|어떤 클래스에서 상속을 허용하고 싶다면 open class *** 처럼 클래스 앞에 open을 붙여야한다.|
|final|코틀린 클래스와 메서드의 기본적 변경자이다.|
|abstract|추상 클래스를 사용하고 싶다면 abstract class *** 처럼 클래스 앞에 abstract를 붙인다. 이때 추상 클래스는 사용성으로 보았을 때 기본적으로 상속을 허용하므로 open 변경자를 붙여주지 않아도 된다. |

final  변경자의 경우 기본으로 설정되기 때문에 따로 설정해줄 필요가 없다.
어떤 클래스에서 상속을 허용하고 싶다면 클래스 앞에 open 변경자를 붙여주면 된다. 이때, override를 하고 싶은 함수에는 open 변경자를 override를 하면 안되는 함수에는 final을 붙여줄 수 있다.

~~~kotlin
interface Clickable {
	fun clickOpen()
	fun clickFinal()
}

open class RichButton : Clickable {
	fun disable() {} // 기본 변경자가 final로 하위 클래스는 오버라이드 할 수 없다.
	open fun animate() {} // 열려 잇는 함수로 하위 클래스에서 오버라이드 해도 된다.
	override fun clickOpen() {} // Clickable을 오버라이드한다. 오버라이드 함수는 기본적으로 열려있다.
	final override fun clickFinal() {} // Clickable을 오버라이드한다. final 키워드로 지정하였으므로 하위 클래스에서 오버라이드 할 수 없다.
}
~~~

🥕 예제를 통해 다음의 정보를 얻을 수 있다. 
- 상속 또는 오버라이드를 허용하기 위해서는 open 키워드를 넣는 것
- 상위 클래스의 함수를 오버라이드 하고, 하위 클래스에서는 오버라이드를 못하게 하기 위해서는 오버라이드한 함수 앞에 final 키워드를 넣어준다.

#### 가시성 변경자
> 가시성 변경자는 코드 기반에 있는 선언에 대한 클래스 외부 접근을 제어한다. 어떤 클래스의 구현에 대한 접근을 제한함으로써 그 클래스에 의존하는 외부 코드를 깨지 않고도 클래스 내부 구현을 변경할 수 있다.
> kotlin in action p.150

|변경자|클래스 멤버|최상위 선언|설명|
|-|-|-|-|
|public|모든 곳에서 볼 수 있다.| 모든 곳에서 볼 수 있다.| 코틀린 클래스와 메서드의 기본 변경자이다.|
|internal|같은 모듈 안에서만 볼 수 있다.| 같은 모듈 안에서만 볼 수 있다.| internal 접근자를 가진 클래스는 확장 함수를 만들 수 없다. |
|protected| 하위 클래스 안에서만 볼 수 있다. | (최상위 선언에 적용할 수 없음) | ... |
|private| 같은 클래스 안에서만 볼 수 있다. | 같은 파일 안에서만 볼 수 있다.| 확장 함수 만들 때 private 멤버에는 접근할 수 없다. |

~~~kotlin
internal open class Coffee {
	private fun drink() {}
	protected fun make() {}
}

fun Coffee.drinkAndMake() { // internal 수신 타입인 Coffee를 노출시킴
	drink() // private 멤버에는 접근할 수 없음
	make() // protected 변경자에 접근할 수 없음
}
~~~

### sealed 클래스
---
[sealed 클래스를 사용하여 else 분기 처리 없애기](https://righteous.tistory.com/25)에서 sealed 클래스에 대한 설명과 예제를 설명하므로 대체하겠다.

### 생성자
---
객체를 생성하기 위해서는 생성자를 필수적으로 사용해야한다. 코틀린의 디폴트 파라미터를 사용하면 여러 방식의 생성자가 기본으로 생성된다. 부생성자와 디폴트 파리미터를 사용하면 어떤 생성 방식을 사용할 수 있는지 아래 코드를 통해 알아보겠다.

~~~kotlin
class Coffee(  
    val name: String = "기본 이름",  
    val price: Long  
) {  
    constructor(info: CoffeeInfo) : this(info.name, info.price)  
  
    override fun toString(): String {  
        return "Coffee(name=$name, price=$price)"  
    }  
}  
  
data class CoffeeInfo(  
    val name: String,  
    val price: Long  
)

>> val coffee1 = Coffee("아메리카노", 5000L)
>> val coffee2 = Coffee(price = 5000L)
>> val coffee3 = Coffee(CoffeeInfo("카페라떼", 5500L))
>> println(listOf(coffee1, coffee2, coffee3))
>> [Coffee(name=아메리카노, price=5000), Coffee(name=기본 이름, price=5000), Coffee(name=카페라떼, price=5500)]
~~~

코드의 마지막에서도 확인할 수 있듯이 위 코드는 3개의 생성자 방식을 제공한다. coffee1부터 coffee3까지 알아보겠다.
- coffee1: 기본 생성자로 Coffee 클래스의 멤버를 모두 넣어 생성하였다.
- coffee2: 디폴트 파리미터와 이름 붙인 인자 문법을 사용하여 price 값만 넣어 생성하였다.
- coffee3: 부생성자를 통해 Cofffee의 정보를 가지고 있는 info 객체를 받아 객체를 생성하였다.

🥕 이 중에서도 디폴트 파리미터를 사용하면 부생성자의 코드를 많이 줄 일 수 있을 것이다. 따라서 부 생성자가 필요한 상황 또는 요구사항이 있다면 디폴트 파라미터 사용을 고려해보면 좋을 것 같다.

### object 키워드
---


