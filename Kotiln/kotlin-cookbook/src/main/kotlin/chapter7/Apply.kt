package chapter7

import kotlin.random.Random

data class PersonDto(
    val name: String,
    val age: Int
) {
    fun toEntity() = Person(name, age)
}

class Person(
    var name: String = "Bob",
    var age: Int = 10
) {
    override fun toString(): String {
        return "Person(name=$name, age=$age)"
    }
}

fun save(person: Person) =
    person.apply {
        name = "bob"
        age = 23
    }

fun main() {
    println("save(Person()) = ${save(Person())}")
}
