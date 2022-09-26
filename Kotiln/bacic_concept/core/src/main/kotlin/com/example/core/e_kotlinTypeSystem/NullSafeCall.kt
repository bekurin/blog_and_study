package com.example.core.e_kotlinTypeSystem

import com.example.core.a_kotlinBasic.iterator.printLine

class Employee(val name: String, val manager: Employee?)

data class Address(val streetAddress: String, val zipCode: Int, val city: String, val country: String)

class Company(val name: String, val address: Address?)

class Person(val name: String, val company: Company?)

fun Person.countryName(): String {
    val country = this.company?.address?.country
    return if (country != null) country else "Unknown"
}

fun manageName(employee: Employee): String? = employee.manager?.name

/**
 * .?를 통해 null 검사와 함수 호출을 동시에 진행할 수 있다.
 */
fun main() {
    val ceo = Employee("jake", null)
    val developer = Employee("hangman", ceo)

    println("manageName(ceo) = ${manageName(ceo)}")
    println("manageName(developer) = ${manageName(developer)}")
    printLine()

    val person = Person("James", null)
    println(person.countryName())
}