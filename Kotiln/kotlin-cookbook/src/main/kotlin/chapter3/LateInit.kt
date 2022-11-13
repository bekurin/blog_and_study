package chapter3

class LateInitDemo {
    lateinit var name: String

    fun initializeName() {
        println("Before assignment: ${::name.isInitialized}")
        name = "Default"
        println("After assignment: ${::name.isInitialized}")
    }
}

/**
 * lateinit 변경자는 제약 사항과 함께 var 속성에 사용된다. lazy 대리자는 속성에 처음 접근할 때 평가되는 람다를 받는다.
 *
 * 초기화 비용이 높은 경우 lazy를 사용하게 되면 초기화에 실패할 수 있다.
 */
fun main() {
    LateInitDemo().initializeName()
}