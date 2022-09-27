package com.example.core.f_operatorOverroadingAndConvention

import com.example.core.a_kotlinBasic.iterator.printLine

data class Person(val firstName: String, val lastName: String, val age: Int) : Comparable<Person> {
    override fun compareTo(other: Person): Int {
        return compareValuesBy(this, other, Person::age, Person::firstName, Person::lastName)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Person) return false

        return other.firstName == firstName &&
                other.lastName == lastName &&
                other.age == age
    }
}

/**
 * Comparable 의 compareTo 를 통해 다른 객체와 비교를 한다.
 * compareValuesBy 를 사용하면 두 객체의 비교값을 순서대로 설정할 수 있다.
 */
fun main() {
    val p1 = Person("Alice", "Smith", 20)
    val p2 = Person("Bob", "Johnson", 20)

    println("p1 == p2 = ${p1 == p2}")
    println("Person(\"a\", \"b\", 10), Person(\"a\", \"b\", 10) = ${Person("a", "b", 10) == Person("a", "b", 10)}")
    println("p1 == null = ${p1 == null}")
    printLine()

    println("p1 < p2 = ${p1 < p2}")
}