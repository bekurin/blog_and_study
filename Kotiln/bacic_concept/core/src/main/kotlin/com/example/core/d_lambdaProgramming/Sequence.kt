package com.example.core.d_lambdaProgramming

data class SequencePerson(val name: String, val age: Int)

/**
 * map 은 모든 원소에 대해서 연산을 실행하기 때문에 filter 를 사용하여 불필요한 원소를 제거하는 것이 좋다.
 * sequence로 연산을 하면 중간 결과들에 대해서 저장하지 않아도 되기 때문에 많은 연산을 필요로 할 때 사용한다.
 */
fun main() {
    val people = listOf(SequencePerson("alice", 10), SequencePerson("bob", 15), SequencePerson("james", 20), SequencePerson("john", 25))

    // X
    val result1 = people.asSequence()
            .map { it.name }
            .filter { it.startsWith('a') }
            .toList()

    // O
    val result2 = people.asSequence()
            .filter { it.name.startsWith('a') }
            .map(SequencePerson::name)
            .toList()

    println("result = $result1")
    println("result2 = $result2")
}