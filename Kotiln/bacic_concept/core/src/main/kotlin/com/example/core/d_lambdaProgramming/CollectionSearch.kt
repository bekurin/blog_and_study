package com.example.core.d_lambdaProgramming

import kotlin.math.max

data class LambdaPerson(val name: String, val age: Int)

fun findTheOldest(people: List<LambdaPerson>): LambdaPerson? {
    var maxAge = 0
    var theOldest: LambdaPerson? = null

    for (person in people) {
        if (person.age > maxAge) {
            theOldest = person
            maxAge = person.age
        }
    }
    return theOldest
}

fun main() {
    val people = listOf(LambdaPerson("Alice", 10), LambdaPerson("Bob", 20), LambdaPerson("Hangman", 25))
    println("findTheOldest(people) = ${findTheOldest(people)}")
    println("people.maxByOrNull() { p: LambdaPerson -> p.age } = ${people.maxByOrNull() { p: LambdaPerson -> p.age }}")
    println("--------------------------------------")
    val sum = {x: Int, y: Int -> x + y}
    println("sum(10, 20) = ${sum(10, 20)}")
}