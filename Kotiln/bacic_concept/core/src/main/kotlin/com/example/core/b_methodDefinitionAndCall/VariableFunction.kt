package com.example.core.b_methodDefinitionAndCall

fun variablePrint(vararg values: String) {
    val list = listOf(*values)
    println(list)
}

fun main(args: Array<String>) {
    variablePrint("one", "two", "three")
}