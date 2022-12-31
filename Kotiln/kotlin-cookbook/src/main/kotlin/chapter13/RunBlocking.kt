package chapter13

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * RunBlocking은 main 스레드를 정지시키고 코루틴을 실행한다.
 * 따라서, 동시성을 달성하기 위해 사용한다는 느낌 보다는 테스트 코드를 작성할 때 결과를 확인하기 위함과 같은 특수한 상황에서 사용해야할 것 같다.
 */
fun main() {
    println("[${Thread.currentThread().name}] Before creating coroutine")
    runBlocking {
        println("[${Thread.currentThread().name}] Hello")
        delay(300L)
        println("[${Thread.currentThread().name}] World")
    }
    println("[${Thread.currentThread().name}] After coroutine is finished")
}