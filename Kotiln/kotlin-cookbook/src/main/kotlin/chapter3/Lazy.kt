package chapter3

class Customer(
    val name: String
) {
    private var _messages: List<String>? = null

    val messages: List<String>
        get() {
            if (_messages == null) {
                _messages = loadMessages()
            }
            return _messages!!
        }

    private fun loadMessages(): MutableList<String> =
        mutableListOf(
            "Initial contact",
            "Convinced them to use Kotlin",
            "Sold training class. Sweet"
        ).also { println("Loaded messages") }
}

/**
 * lazy 대리자를 통해 messages가 필요한 시점에 loadMessages 를 실행하여 messages 값을 초기화한다.
 */
class CustomerLazy(
    val name: String
) {
    val messages: List<String> by lazy { loadMessages() }

    private fun loadMessages(): MutableList<String> =
        mutableListOf(
            "Initial contact",
            "Convinced them to use Kotlin",
            "Sold training class. Sweet"
        ).also { println("Loaded messages") }
}
