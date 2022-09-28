package com.example.core.g_hignOrderFunctionAndLambda

import com.example.core.a_kotlinBasic.iterator.printLine

val sum: (Int, Int) -> Int = { x, y -> x + y }

fun twoAndThree(operation: (Int, Int) -> Int): Int {
    return operation(2, 3)
}

fun String.filter(predicate: (Char) -> Boolean): String {
    val sb = StringBuilder()
    for (index in 0 until length) {
        val element = get(index)
        if (predicate(element)) sb.append(element)
    }
    return sb.toString()
}

/**
 * fun String.twoAndThree(operation: (Int, Int) -> Int): Int {}
 *    / 수신객체 / 함수명 / 파라미터 이름 / 입력 타입 / 반환 타입 / 함수 반환 타입
 * 함수를 인자처럼 다룰 수 있다.
 */
fun main() {
    println("sum(2,3) = ${sum(2, 3)}")
    printLine()

    println("twoAndThree(sum) = ${twoAndThree(sum)}")
    println("twoAndThree { x, y -> x * y } = ${twoAndThree { x, y -> x * y }}")
    println("twoAndThree { x, y -> x / y } = ${twoAndThree { x, y -> x / y }}")
    printLine()

    println("\"he!#l35lo w()or245ld\".filter { it in 'a'..'z'} = ${"he!#l35lo w()or245ld".filter { it in 'a'..'z' }}")
}