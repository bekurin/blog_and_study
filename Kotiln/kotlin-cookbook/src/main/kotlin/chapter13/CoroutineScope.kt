package chapter13

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * GlobalScope의 launch와 async를 사용하지 마라
 * GlobalScope로 정의된 코루틴은 잡을 취소하지 않으면 어플리케이션 전반에 걸쳐 실행되기 때문이다.
 */
suspend fun main() {
    var numberList = mutableListOf<Int>()
    coroutineScope {
        for (i in 0 until 10) {
            launch {
                delay(1000L - i * 10)
                numberList.add(i)
            }
        }
    }
    println("numberList = ${numberList}")
}