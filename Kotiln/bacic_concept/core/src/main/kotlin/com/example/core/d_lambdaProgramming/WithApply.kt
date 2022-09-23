package com.example.core.d_lambdaProgramming

import com.example.core.a_kotlinBasic.iterator.printLine

fun alphabet(): String {
    val result = StringBuilder()
    for(letter in 'A'..'Z')
        result.append(letter)
    return result.toString()
}

/**
 * 객체의 이름을 반복하지 않고 반복문 사용가능 (result 대신에 this를 사용)
 */
fun alphabetWith(): String {
    val result = StringBuilder()
    return with(result) {
        for (letter in 'A'..'Z')
            this.append(letter)
        this.toString()
    }
}

/**
 * with 보다 더 간결하게 하여 객체의 이름을 사용하지 않음
 */
fun alphabetApply() = StringBuilder().apply {
    for (letter in 'A'..'Z')
        append(letter)
}

fun main() {
    println("alphabet() = ${alphabet()}")
    println("alphabetWith() = ${alphabetWith()}")
    println("alphabetApply() = ${alphabetApply()}")
}