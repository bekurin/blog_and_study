package com.example.core.e_kotlinTypeSystem

import com.example.core.a_kotlinBasic.iterator.printLine
import java.lang.IllegalStateException

interface Processor<T> {
    fun process(): T
}

class NoResultProcessor: Processor<Unit> {
    override fun process() {
        println("return nothing")
    }
}

fun fail(message: String): Nothing {
    throw IllegalStateException(message)
}

/**
 * Any: 원시 타입을 포함하는 자바의 Object
 * Unit: 자바의 void 와 같은 기능
 * Nothing: 정상적인 종료를 기대하지 않음 따라서 반환값이 존재하지 않는다.
 */
fun main() {
    var answer: Any = 42
    println("answer = $answer")
    answer = "hello"
    println("answer = $answer")
    printLine()

    NoResultProcessor().process()
    printLine()

    fail("Error occurred!!")
}