package com.example.core.f_operatorOverroadingAndConvention

/**
 * +=, -=, *= 과 같은 연산들도 오버로딩할 수 있다.
 */
fun main() {
    val list = arrayListOf(1, 2)
    list += 3
    println("list = $list")
}