package com.example.core.a_kotlinBasic.iterator

import java.util.TreeMap

fun fizzbuzz(i: Int) =
        when {
            i % 15 == 0 -> "FizzBuzz"
            i % 3 == 0 -> "Fizz"
            i % 5 == 0 -> "Buzz"
            else -> "$i"
        }

fun getBinaryReps(): TreeMap<Char, String> {
    val binaryReps = TreeMap<Char, String>()
    for (c in 'A'..'Z') {
        val binary = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binary
    }
    return binaryReps
}

fun printLine() {
    println("------------------------------")
}

fun main() {
    println("1 Up To 100")
    for (i in 1..100) {
        println("fizzbuzz($i) = ${fizzbuzz(i)}")
    }
    printLine()
    println("100 Down To 1 step 2")
    for (i in 100 downTo 1 step 2) {
        println("fizzbuzz($i) = ${fizzbuzz(i)}")
    }

    printLine()
    for ((letter, binary) in getBinaryReps()) {
        println("$letter = $binary")
    }

    printLine()

    val list = arrayListOf("10", "11", "101")
    for ((index, element) in list.withIndex()) {
        println("$index's item = $element")
    }
}