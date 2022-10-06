package com.example.core.l_tip

import com.example.core.a_kotlinBasic.iterator.printLine

class Person(
        val id: Long? = null,
        val nickname: String = "홍길동",
        val age: Int = 0
) {
    override fun toString(): String {
        return "Person(id=$id, nickname='$nickname', age=$age)"
    }
}

class PersonRepository() {
    private val people = listOf(
            Person(1L),
            Person(2L, "아무개", 22),
            Person(3L, "행맨", 25),
            Person(4L, "햄버거", 23)
    )

    fun findById(id: Long): Person? {
        return people.firstOrNull {
            it.id == id
        }
    }
}

fun PersonRepository.getByIdOrThrow(id: Long): Person {
    return findById(id)
            ?: throw IllegalArgumentException("Person object or id filed is null. id: $id")
}

/**
 * Jpa 를 사용하여 구현된 findById의 경우 null 체크를 해주지 않아 getByIdOrThrow 라는 확장함수를 만들어서 null 체크를 해주는 함수를 간편하게 추가할 수 있다.
 */
fun main() {
    val personRepository = PersonRepository()
    val people = (1..4).map {
        personRepository.getByIdOrThrow(it.toLong())
    }.toList()

    for (person in people) {
        println("person = $person")
    }
    printLine()

    personRepository.getByIdOrThrow(5L)
}