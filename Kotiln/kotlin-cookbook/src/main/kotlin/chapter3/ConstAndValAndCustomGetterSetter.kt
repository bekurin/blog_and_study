package chapter3

import util.printLine

class Task(
    val name: String,
    _priority: Int = DEFAULT_PRIORITY
) {
    companion object {
        const val MIN_PRIORITY = 1
        const val MAX_PRIORITY = 5
        const val DEFAULT_PRIORITY = 3
    }

    var priority = validPriority(_priority)
        set(value) {
            field = validPriority(value)
        }

    val isLowPriority
        get() = priority < 3

    private fun validPriority(p: Int) =
        p.coerceIn(MIN_PRIORITY, MAX_PRIORITY)
}

/**
 * const는 private, inline과 같은 키워드이다. 따라서 const는 val과 함께 사용되어야한다.
 * custom getter, setter를 생성할 수 있다. custom getter의 경우 매번 값을 생성하여 전달한다는 것을 유의해야한다.
 * custom setter의 경우 initializer가 필요하다.
 */
fun main() {
    val first = Task("First")
    first.priority = 4
    println("first.priority = ${first.priority}")
    println("first.isLowPriority = ${first.isLowPriority}")
    printLine()

    first.priority = 6
    println("first.priority = ${first.priority}")
    println("first.isLowPriority = ${first.isLowPriority}")
}