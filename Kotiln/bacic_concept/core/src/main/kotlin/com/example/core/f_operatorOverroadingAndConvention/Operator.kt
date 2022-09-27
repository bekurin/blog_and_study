package com.example.core.f_operatorOverroadingAndConvention

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun times(scale: Double): Point {
        return Point((x * scale).toInt(), (y * scale).toInt())
    }
}

operator fun Point.div(scale: Double): Point {
    return Point((x / scale).toInt(), (y / scale).toInt())
}

/**
 * operator 와 정해진 키워드를 사용하여 연산자 오버로딩을 할 수 있다.
 * 연산자 오버로딩은 교환 법칙을 지원하지 않으므로 0.5 * point1 과 같은 연산을 지원하지 않는다.
 * times, div, mod(rem), plus, minus
 */
fun main() {
    val point1 = Point(10, 10)
    val point2 = Point(20, 20)

    println("point1 + point2 = ${point1 + point2}")
    println("point1 * 0.5 = ${point1 * 0.5}")
    println("point2 = ${point2 / 2.0}")
}