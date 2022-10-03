package com.example.core.h_generics

import com.example.core.a_kotlinBasic.iterator.printLine

fun <T: Number> oneHalf(value: T): Double {
    return value.toDouble() / 2.0
}

fun <T: Comparable<T>> max(first: T, second: T): T {
    return if (first > second) first else second
}

fun <T> ensureTrailingPeriod(seq: T)
    where T: CharSequence, T: Appendable {
        if (!seq.endsWith(".")) {
            seq.append(".")
        }
    }

/**
 * 타입 파라미터 제약: 클래스나 함수에 사용할 수 있는 타입 인자를 제한하는 기능이다.
 * oneHalf 의 경우 Number 하위 타입만 입력으로 넣을 수 있다.
 * 형식: <T: 상한 타입> ex) <T: Number>
 * 타입 파라미터에 여러 제약을 가할 수 있다.
 */
fun main() {
    println("oneHalf(30) = ${oneHalf(30)}")
    printLine()

    println("max(\"kotlin\", \"java\") = ${max("kotlin", "java")}")
    printLine()

    val helloWorld = StringBuilder("Hello World")
    ensureTrailingPeriod(helloWorld)
//    ensureTrailingPeriod(10)
    println("helloWorld = $helloWorld")
}