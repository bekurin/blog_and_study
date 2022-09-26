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

/**
 * 엘비스 연산자를 사용하면 쉽게 null 타입 검사를 실행할 수 있다.
 */
fun main() {
    val address = Address("streetAddress", 34753, "Seoul", "Republic of korea")
    val jetBrains = Company("JetBrains", address)
    val bob = Person("Bob", jetBrains)
    val john = Person("John", null)

    printShippingLabel(bob)
    printShippingLabel(john)
}