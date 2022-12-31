package chapter13

import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.random.Random

private suspend fun busyWork() {
    delay(1L)
    List(7000000) { Random.nextLong() }.maxOrNull()
}

private suspend fun readUrl(url: URL, index: Int) {
    val connection = url.openConnection()
    delay(1)
    BufferedReader(InputStreamReader(connection.getInputStream())).use { buffer ->
        var line: String?
        while (buffer.readLine().also { line = it } != null) {
            if (line?.startsWith("<!DOCTYPE html>") == true) {
                println("[dispatcherIO$$index] ${line?.substring(0 until 15)}")
            }
        }
    }
}

suspend fun dispatcherIO(count: Int) {
    return coroutineScope {
        repeat(count) { index ->
            launch(Dispatchers.IO) {
                println("[${Thread.currentThread().name}] Before readUrl on dispatcherIO$$index")
                readUrl(URL("https://www.facebook.com/"), index)
                println("[${Thread.currentThread().name}] After readUrl on dispatcherIO$${index}")
            }
        }
    }
}

suspend fun dispatcherDefault(count: Int) {
    return coroutineScope {
        repeat(count) { index ->
            launch(Dispatchers.Unconfined) {
                println("[${Thread.currentThread().name}] Before doBusyWork on dispatcherDefault$$index")
                busyWork()
                println("[${Thread.currentThread().name}] After doBusyWork on dispatcherDefault$$index")
            }
        }
    }
}

suspend fun dispatcherUnconfined(count: Int) {
    return coroutineScope {
        repeat(count) { index ->
            launch(Dispatchers.Unconfined) {
                println("[${Thread.currentThread().name}] Before delay on dispatcherUnconfined$$index")
                delay(Random.nextLong(10))
                println("[${Thread.currentThread().name}] First delay on dispatcherUnconfined$$index")
                delay(Random.nextLong(100))
                println("[${Thread.currentThread().name}] Second delay on dispatcherUnconfined$$index")
            }
        }
    }
}

fun main() {
    runBlocking {
        val count = 100
        var (start, end) = Pair(0L, 0L)

        start = System.currentTimeMillis()
//        dispatcherIO(count)
        dispatcherDefault(count)
//        dispatcherUnconfined(count)
        end = System.currentTimeMillis()
        println("Average Time = ${(end - start) / count}ms")
    }
}
