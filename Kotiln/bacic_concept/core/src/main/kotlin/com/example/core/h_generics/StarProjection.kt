package com.example.core.h_generics

import com.example.core.a_kotlinBasic.iterator.printLine

fun printSum(c: Collection<*>) {
    val intList = c as? List<Int>
            ?: throw IllegalArgumentException("List is expected!")
    println("intList.sum() = ${intList.sum()}")
}

fun printSumKnowType(c: Collection<Int>) {
    if (c is List<Int>) {
        println("c.sum() = ${c.sum()}")
    }
}

/**
 * 스타 프로젝션 *을 사용하여 어떤 값이 집합, 맵, 리스트라는 것을 확인할 수 있다.
 */
fun main() {
    val intList = listOf(1, 2, 3)
    val intSet = setOf(1, 2, 3)
    val charList = listOf("a", "b", "c")

    printSum(intList)
    printLine()

    printSum(intSet)
    printSum(charList)
}