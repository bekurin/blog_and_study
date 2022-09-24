package com.example.core.e_kotlinTypeSystem

class Employee(val name: String, val manager: Employee?)

fun manageName(employee: Employee): String? = employee.manager?.name

fun main() {
    val ceo = Employee("jake", null)
    val developer = Employee("hangman", ceo)

    println("manageName(ceo) = ${manageName(ceo)}")
    println("manageName(developer) = ${manageName(developer)}")
}