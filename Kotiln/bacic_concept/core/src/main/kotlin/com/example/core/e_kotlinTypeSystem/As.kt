package com.example.core.e_kotlinTypeSystem

class AsPerson(val firstName: String, val lastName: String) {
    override fun equals(other: Any?): Boolean {
        val otherPerson = other as? AsPerson ?: return false
        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }

    override fun hashCode(): Int =
            firstName.hashCode() * 37 + lastName.hashCode()
}

/**
 * as를 사용하여 지정한 타입으로 cast를 한다.
 * 엘비스 연산자와 함께 사용하면 타입 확인하는 코드를 간결하게 작성할 수 있다.
 */
fun main() {
    val p1 = AsPerson("Dmitry", "Jemerov")
    val p2 = AsPerson("Dmitry", "Jemerov")
    println("p1 == p2 = ${p1 == p2}")
    println("p1.equals(42) = ${p1.equals(42)}")
}