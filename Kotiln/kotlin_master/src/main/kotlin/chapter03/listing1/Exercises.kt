package chapter03.listing1

import java.lang.Math.*
import kotlin.math.sin

val cos = exercise5<Double, Double, Double>()() { x: Double -> PI / 2 - x }(Math::sin)

// 타입 추론 사용하기
fun <T, U, V> compose(f: (U) -> V, g: (T) -> U): (T) -> V = { f(g(it)) }
fun cosV1(arg: Double) =
    compose(
        { x: Double -> PI / 2 - x },
        { x: Double -> sin(x) })(arg)

// 로컬 함수 정의하기
fun cosV2(arg: Double): Double {
    fun f(x: Double): Double = PI / 2 - x
    fun sin(x: Double): Double = sin(x)
    return compose(::f, ::sin)(arg)
}

// 클로저
val addTaxV1 = { taxRate: Double, price: Double -> price + price * taxRate }
val addTaxV2 = { taxRate: Double -> { price: Double -> price + price * taxRate } }

fun main() {
    val (taxRate, price) = Pair(0.09, 1900.0)
    println(addTaxV1(taxRate, price))
    println(addTaxV2(taxRate)(price))
}
