package com.example.core.d_lambdaProgramming

import com.example.core.a_kotlinBasic.iterator.printLine

data class MemberReferencePerson(val name: String, val age: Int)

fun MemberReferencePerson.sendEmail(person: MemberReferencePerson, message: String) {
    println("send ${person.name}, msg: $message, to: $name")
}

fun MemberReferencePerson.isAdult() = age >= 20


fun main() {
    val createPerson = ::MemberReferencePerson
    val alice = createPerson("Alice", 29)
    println("Alice = ${alice}")
    printLine()

    val nextAction = alice::sendEmail
    println(nextAction(createPerson("Bob", 25), "hello Alice"))
    printLine()

    val predict = MemberReferencePerson::isAdult
    println("predict(alice) = ${predict(alice)}")
    println("predict(createPerson(\"james\", 15)) = ${predict(createPerson("james", 15))}")
}