package chapter13

import kotlinx.coroutines.*
import java.util.concurrent.Executors

suspend fun launchWithIO() {
    withContext(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] Using Dispatcher IO")
    }
}

suspend fun launchWithDefault() {
    withContext(Dispatchers.Default) {
        delay(1000L)
        println("[${Thread.currentThread().name}] Using Dispatcher Default")
    }
}

/**
 * 코루틴에는 3가지의 Dispatcher가 있다.
 * IO: 파일, 블로킹의 I/O 제거를 위해 최적화된 공유 스레드 풀을 사용한다.
 * Default: 기본적인 공유 백그라운드 스레드 풀을 사용한다.
 * Unconfined: 코루틴을 실행한 스레드만 사용하도록 동작한다. (같은 스레드만 사용하는 것이라면 default가 더 좋은 선택일 것 같다.)
 */
fun main() {
    runBlocking {
        val dispatcher = Executors.newFixedThreadPool(10)
            .asCoroutineDispatcher()
        dispatcher.use {
            withContext(dispatcher) {
                delay(1000L)
                println("[${Thread.currentThread().name}] Using Thread Pool")
            }
        }

        launchWithIO()
        launchWithDefault()
    }
}