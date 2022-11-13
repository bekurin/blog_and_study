package chapter3

/**
 * 논리적으로 동등한 인스턴스인지 확인하기 위해 equals 메서드를 잘 구현하고 싶다.
 * ===, as?, ?: 를 사용하여 equals 를 구현한다.
 */
class Person(
    val name: String
) {

    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        val otherPerson = (other as? Person) ?: return false
        return this.name == otherPerson.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

