package com.example.core.a_kotlinBasic

class KotlinClass(
        val name: String, // readonly, getter
        var isOptional: Boolean // getter, setter
        ) {
}

class Rectangle(var width: Int, var height: Int) {
    val isSquare: Boolean
        get() {
            return width == height
        }
}


fun main() {
    val kotlinClass = KotlinClass("hangman", false)
    println("kotlinClass.name = ${kotlinClass.name}")
    println("kotlinClass.isOptional = ${kotlinClass.isOptional}")
    kotlinClass.isOptional = true
    println("kotlinClass.isOptional = ${kotlinClass.isOptional}")

    val rectangle = Rectangle(3, 3)
    println("rectangle.isSquare = ${rectangle.isSquare}")
    rectangle.height = 4
    println("rectangle.isSquare = ${rectangle.isSquare}")
}