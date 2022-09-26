package com.example.core.e_kotlinTypeSystem

fun ignoreNulls(s: String) {
    val s1: String = s!!
    println(s1.length)
}

/**
 * !!는 null이 아님을 단언하기 때문에 널값을 할당하면 컴파일 타임에 에러를 발생시킨다.
 */
fun main() {
//    ignoreNulls(null)
    ignoreNulls("i am not null")
}