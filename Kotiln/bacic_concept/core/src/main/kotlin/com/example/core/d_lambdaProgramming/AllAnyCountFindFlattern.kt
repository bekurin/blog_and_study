package com.example.core.d_lambdaProgramming

import com.example.core.a_kotlinBasic.iterator.printLine

fun main() {
    val oneTo100 = listOf(1,2,3,4,5,6,7,8,9,10)
    val lessThan5 = {number: Int -> number <= 5}

    println("oneTo100.all(lessThan5)} = ${oneTo100.all(lessThan5)}}")
    println("oneTo100.any(lessThan5) = ${oneTo100.any(lessThan5)}")
    printLine()

    println("oneTo100.count(lessThan5) = ${oneTo100.count(lessThan5)}")
    printLine()

    println("oneTo100.find(lessThan5) = ${oneTo100.find(lessThan5)}")
    println("oneTo100.firstOrNull(lessThan5) = ${oneTo100.firstOrNull(lessThan5)}")
    printLine()

    val strings = listOf("abc", "def", "ghi")
    println("strings.flatMap { it.toList() } = ${strings.flatMap { it.toList() }}")
}