package chapter13

import kotlinx.coroutines.*

/**
 * 코루틴 cancel()을 통해 중단 시킬 수 있다.
 * 시간에 따라 중단이 필요하다면 withTimeout, withTimeoutOrNull 을 고려해볼 수 있다.
 * 중지 or 예외가 발생한다고 해도 값을 rollback 시키지 않는다.
 */
fun main() {
    runBlocking {
        val job = launch {
            repeat(100) { i ->
                println("Job: I'm waiting $i")
                delay(100L)
            }
        }
        delay(1000L)
        println("main: That's not enough waiting")
        job.cancel()
        job.join()
        println("main: Done")
    }

    val result = mutableListOf<Int>()
    runBlocking {
        try {
            withTimeoutOrNull(1000L) {
                repeat(100) { i ->
                    println("Job: I'm waiting $i")
                    delay(100L)
                    result.add(i)
                }
            }
            throw RuntimeException()
        } catch (e: Exception) {
            println("result = $result")
        }
    }
    println("result = $result")
}