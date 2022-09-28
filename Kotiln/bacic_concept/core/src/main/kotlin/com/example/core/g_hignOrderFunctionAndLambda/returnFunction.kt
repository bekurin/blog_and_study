package com.example.core.g_hignOrderFunctionAndLambda

enum class Delivery {STANDARD, EXPEDITED}
class Order(val ItemCount: Int)

fun getShippingCostCalculator(
        delivery: Delivery
): (Order) -> Double {
    if (delivery == Delivery.EXPEDITED) {
        return {order -> 6 + 2.1 * order.ItemCount}
    }
    return {order -> 1.2 * order.ItemCount}
}

/**
 * 함수를 반환하는 함수를 만들 수 있다.
 */
fun main() {
    val order = Order(10)
    val expedited = getShippingCostCalculator(Delivery.EXPEDITED)
    val standard = getShippingCostCalculator(Delivery.STANDARD)

    println("expedited(order) = ${expedited(order)}")
    println("standard(order) = ${standard(order)}")
}