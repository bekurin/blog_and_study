package com.example.core.b_methodDefinitionAndCall

fun main() {
    val numberList = arrayListOf(1, 2, 3, 4)
    val map = hashMapOf(1 to "one", 2 to "two", 3 to "third", "key" to "string key")

    println(numberList.javaClass)
    println(map.javaClass)
    println("numberList.last() = ${numberList.last()}")
}
