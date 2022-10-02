package com.example.core.h_generics

import com.example.core.a_kotlinBasic.iterator.printLine

inline fun <reified T> isA(value: Any) = value is T

//fun <T> isAFail(value: Any) = value is T

inline fun <reified T>
        Iterable<*>.filterIsInstance(): List<T> {
    val destination = mutableListOf<T>()
    for (element in this) {
        if (element is T) {
            destination.add(element)
        }
    }
    return destination
}

/**
 * 보통 제너릭 함수가 호출되도 함수의 본문에서는 호출 시 쓰인 타입 인자를 알 수 없다.
 * 하지만 inline 키워드를 사용하면 실행 시점에 타입 파라미터가 실체화 되므로 inline 함수의 타입 인자를 알 수 있다.
 * 따라서 실체화한 타입 파리미터의 사용이 필요하다면 inline 함수와 reified 키워드를 사용할 수 있다.
 */
fun main() {
    println("isA<String>(\"abc\") = ${isA<String>("abc")}")
    println("isA<String>(123) = ${isA<String>(123)}")
    printLine()

    val items = listOf("one", 2, "three")
    println("items.filterIsInstance<String>() = ${items.filterIsInstance<String>()}")
}