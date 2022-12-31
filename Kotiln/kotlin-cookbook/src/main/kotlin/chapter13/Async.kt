package chapter13

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun add(x: Int, y: Int): Int {
    delay(1000L)
    return x + y
}

/**
 * 순서를 바꿔 실행해 보아도 first가 second보다 먼저 실행된다. 왜??
 * async를 한 시점은 계산을 정의하는 시점
 * await는 계산을 실행하는 시점
 * 미리 계산하여 메모리에 저장하는 것이 아닌 필요할 때에 필요한 계산을 실행하겠다는게 기본 개념인 것 같다.
 */
suspend fun main() {
    coroutineScope {
        val firstSum = async {
            println("[${Thread.currentThread().name}] firstSum")
            add(2, 2)
        }

        val secondSum = async {
            println("[${Thread.currentThread().name}] secondsum")
            add(3, 4)
        }

        println("Awaiting concurrent sums...")
        var total = secondSum.await()
        total += firstSum.await()

        println("Total is $total")
    }
}