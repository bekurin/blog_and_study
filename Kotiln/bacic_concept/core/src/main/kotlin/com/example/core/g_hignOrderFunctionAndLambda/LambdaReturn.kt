package com.example.core.g_hignOrderFunctionAndLambda

import com.example.core.a_kotlinBasic.iterator.printLine

data class Person(val name: String, val age: Int)

val people = listOf(Person("Alice", 29), Person("Bob", 23))

fun lookForAliceFor(people: List<Person>): Unit {
    for (person in people) {
        if (person.name == "Alice") {
            println("Found!!")
            return
        }
    }
    println("Alice is not found.")
}

fun lookForAliceForEach(people: List<Person>): Unit {
    people.forEach {
        if (it.name == "Alice") {
            println("Found!!")
            return
        }
    }
    println("Alice is not found.")
}

fun lookForAliceLabel(people: List<Person>) {
    people.forEach label@{
        if (it.name == "Alice") {
            println("Found!!")
            return@label
        }
    }
    println("Alice might be somewhere.")
}

fun lookForAliceLabelFunctionName(people: List<Person>) {
    people.forEach {
        if (it.name == "Alice") {
            println("Found!!")
            return@forEach
        }
    }
    println("Alice might be somewhere.")
}

fun lookForAliceAnonymousFunction(people: List<Person>) {
    people.forEach(fun (person) {
        if (person.name == "Alice") {
            println("Found!!")
            return
        }
        println("${person.name} is not Alice")
    })
    println("Alice might be somewhere.")
}

fun main() {
    lookForAliceFor(people)
    printLine()

    lookForAliceForEach(people)
    printLine()

    lookForAliceLabel(people)
    printLine()

    lookForAliceLabelFunctionName(people)
}