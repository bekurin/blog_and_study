package com.example.core.a_kotlinBasic

fun main() {
    printHelloWorld()

    var changeable: Int = maxTraditional(2, 3)
    println("changeable = ${changeable}")
    changeable = maxKotlin(3, 4)
    println("changeable = ${changeable}")

    stringTemplate(arrayListOf("hangman"))
}

fun printHelloWorld() {
    println("Hello, World")
}

fun maxTraditional(a: Int, b: Int): Int {
    return if (a > b) a else b
}

fun maxKotlin(a: Int, b: Int): Int = if (a > b) a else b

fun stringTemplate(args: ArrayList<String>) {
    val name = if (args.isNotEmpty()) args[0] else "Kotlin"
    println("1. Hello, $name")
    println("2. Hello, ${args[0]}")
    println("3. Hello, ${if (args.isNotEmpty()) args[0] else "Kotlin"}")
}

