package com.example.core.f_operatorOverroadingAndConvention

import com.example.core.a_kotlinBasic.iterator.printLine
import java.time.LocalDate

data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean {
    return p.x in upperLeft.x until lowerRight.x &&
            p.y in upperLeft.y until lowerRight.y
}

/**
 * in 연산자에 대응되는 함수는 contains 이다. contains 를 정의하면 조건에 맞게 in 연산을 활용할 수 이싿.
 * rangeTo 는 ..을 사용하여 표현한다. 0..n+1 과 같은 경우 0..(n+1)과 같이 괄호를 넣어주어 혼동을 방지하는 것이 좋다.
 */
fun main() {
    val rectangle = Rectangle(Point(10, 20), Point(50, 50))
    println("Point(20, 30) in rectangle = ${Point(20, 30) in rectangle}")
    println("Point(5, 5) in rectangle = ${Point(5, 5) in rectangle}")
    printLine()

    val now = LocalDate.now()
    val vacation = now..now.plusDays(10)
    println("now.plusWeeks(1) = ${now.plusWeeks(1) in vacation}")
}