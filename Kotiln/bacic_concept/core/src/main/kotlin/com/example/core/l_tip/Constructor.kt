package com.example.core.l_tip

class Coffee(
    val name: String = "기본 이름",
    val price: Long
) {
    constructor(info: CoffeeInfo) : this(info.name, info.price)

    override fun toString(): String {
        return "Coffee(name=$name, price=$price)"
    }
}

data class CoffeeInfo(
    val name: String,
    val price: Long
)

fun main() {
    val coffee1 = Coffee("아메리카노", 5000L)
    val coffee2 = Coffee(price = 5000L)
    val coffee3 = Coffee(CoffeeInfo("카페라떼", 5500L))

    println(listOf(coffee1, coffee2, coffee3))
}