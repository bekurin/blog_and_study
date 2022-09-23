package com.example.core.d_lambdaProgramming

import com.example.core.a_kotlinBasic.iterator.printLine

data class FilterMapPerson(val name: String, val age: Int)

/**
 * filter를 사용하여 조건에 맞는 collection 결과를 얻을 수 있다.
 * map을 사용하여 원하는 필드의 결과만 취합할 수 있다.
 */
fun main() {
    val personList = listOf(FilterMapPerson("Alice", 20), FilterMapPerson("Bob", 25), FilterMapPerson("John", 24))
    println("personList = $personList")
    printLine()

    println("personList.filter { it.age < 24 } = ${personList.filter { it.age <= 24 }}")
    println("personList.map(FilterMapPerson::name) = ${personList.map(FilterMapPerson::name)}")
    printLine()

    println("personList.filter { it.age >= 24 }.map(FilterMapPerson::name) = " +
            "${
                personList
                        .filter { it.age >= 24 }
                        .map(FilterMapPerson::name)
            }")
}