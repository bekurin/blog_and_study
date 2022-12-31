package chapter13

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    println("[${Thread.currentThread().name}] Before runBlocking")
    runBlocking {
        println("[${Thread.currentThread().name}] Before Launch")
        launch {
            println("[${Thread.currentThread().name}] Hello")
            delay(1000L)
            println("[${Thread.currentThread().name}] World")
        }
        println("[${Thread.currentThread().name}] After Launch")
    }
    println("[${Thread.currentThread().name}] After runBlocking")
}