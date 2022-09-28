package com.example.core.g_hignOrderFunctionAndLambda

import com.example.core.a_kotlinBasic.iterator.printLine

fun <T> Collection<T>.joinToString(
        separator: String = ", ",
        prefix: String = "",
        postfix: String = "",
        transform: (T) -> String = { it.toString() }
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(transform(element))
    }
    result.append(postfix)
    return result.toString()
}

fun <T> Collection<T>.nullableJoinToString(
        separator: String = ", ",
        prefix: String = "",
        postfix: String = "",
        transform: ((T) -> String)? = null
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        val str = transform?.invoke(element) ?: element.toString()
        result.append(str)
    }
    result.append(postfix)
    return result.toString()
}

/**
 * 함수를 인자로 받을 수 있다.
 * 파라미터와 마찬가지로 nullable 하게 만들 수 있다.
 * 함수 타입의 파리미터에 대한 디폴트 값을 정의할 수 있다.
 * 함수가 null 인 경우 직접 호출 할 수 없으므로 invoke 와 엘비스 연산자를 사용하여 안전 호출을 하는 것이 좋다.
 */
fun main() {
    val letters = listOf("Alice", "Bob", "John", "Sam")
    println("letters.joinToString() = ${letters.joinToString()}")
    println("letters.joinToString { it.lowercase() } = ${letters.joinToString { it.lowercase() }}")
    println("letters.joinToString(prefix = \"(\", postfix = \")\", transform =  { it.uppercase() } = " +
            letters.joinToString(prefix = "(", postfix = ")", transform = { it.uppercase() }))
    printLine()

    println("letters.nullableJoinToString() } = ${letters.nullableJoinToString()}}")
    println("letters.nullableJoinToString { it.lowercase() } = ${letters.nullableJoinToString { it.lowercase() }}")
    println("letters.nullableJoinToString(prefix = \"(\", postfix = \")\", transform = { it.uppercase() }) = " +
            letters.nullableJoinToString(prefix = "(", postfix = ")", transform = { it.uppercase() }))
}