package com.example.core.e_kotlinTypeSystem

import com.example.core.a_kotlinBasic.iterator.printLine

fun printShippingLabel(person: Person) {
    val address = (person.company?.address
            ?: throw IllegalArgumentException("No Address"))

    with(address) {
        println("person.name = ${person.name}")
        println("toString() = ${toString()}")
        printLine()
    }
}

fun main() {
    val address = Address("streetAddress", 34753, "Seoul", "Republic of korea")
    val jetBrains = Company("JetBrains", address)
    val bob = Person("Bob", jetBrains)
    val john = Person("John", null)

    printShippingLabel(bob)
    printShippingLabel(john)
}