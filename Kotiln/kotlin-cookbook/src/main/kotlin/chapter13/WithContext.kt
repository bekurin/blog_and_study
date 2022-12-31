package chapter13

import kotlinx.coroutines.*

suspend fun retrieveV1(url: String) = coroutineScope {
    async(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] retrieving data on...")
        delay(1000L)
        "async Result"
    }.await()
}

suspend fun retrieveV2(url: String) = withContext(Dispatchers.IO) {
    println("[${Thread.currentThread().name}] retrieving data on...")
    delay(1000L)
    "withContext Result"
}

/**
 * async/await를 사용할 때에 async로 역할을 정의하자마자 await를 사용해야할 경우가 있을 수 있다.
 * 이럴 떄에는 withContext를 사용하여 자동으로 await가 동작하도록 intellij가 권장한다.
 */
fun main() {
    runBlocking {
        val resultV1 = retrieveV1("www.righteous.com")
        val resultV2 = retrieveV2("www.righteous.com")
        println("[${Thread.currentThread().name}] printing resultV1 = $resultV1")
        println("[${Thread.currentThread().name}] printing resultV2 = $resultV2")
    }
}