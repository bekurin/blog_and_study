package com.example.core.f_operatorOverroadingAndConvention

import com.example.core.a_kotlinBasic.iterator.printLine

operator fun Point.get(index: Int): Int {
    return when(index) {
        0 -> x
        1 -> y
        else ->
            throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

data class MutablePoint(var x: Int, var y: Int)
operator fun MutablePoint.set(index: Int, value: Int) {
    when(index) {
        0 -> x = value
        1 -> y = value
        else ->
            throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

/**
 * get, set 함수를 정의하여 index 접근이 가능하다.
 * set 함수를 정의하려면 var 로 변수를 선언해야한다.
 */
fun main() {
    val point = Point(10, 20)
    val mutablePoint = MutablePoint(10, 20)

    println("point = $point")
    println("point[0] = ${point[0]}")
    println("point[1] = ${point[1]}")
    printLine()

    println("mutablePoint = $mutablePoint")
    mutablePoint[0] = 30
    println("mutablePoint = $mutablePoint")
}