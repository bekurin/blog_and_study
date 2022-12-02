package chapter9

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

@JvmOverloads
tailrec fun fibonacci(n: Int, a: Int = 0, b: Int = 1): Int {
    return when (n) {
        0 -> a
        1 -> b
        else -> fibonacci(n - 1, b, a + b)
    }
}

enum class TestType {
    NORMAL, DEFAULT
}

/**
 * Lifecycle.PER_CLASS는
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
class FiboTests {

    @ParameterizedTest
    @CsvSource("1, bob, NORMAL", "2, james, DEFAULT")
    fun `Type Confirm Test`(id: Int, name: String, testType: TestType) {
        println("id=${id}, name=${name}, testType=${testType}")
        println("${id::class.simpleName},${name::class.simpleName},${testType::class.simpleName}")
    }

    @ParameterizedTest
    @CsvSource("1,1", "2,1", "3,2", "4,3", "5,5", "6,8")
    fun `fiboCsvSourceTest`(n: Int, fib: Int) {
        assertEquals(fibonacci(n), fib)
    }

    private fun fibNumbers() = listOf(
        Arguments.of(1, 1),
        Arguments.of(2, 1),
        Arguments.of(4, 3),
        Arguments.of(6, 8),
        Arguments.of(3, 2),
        Arguments.of(5, 5)
    )

    @ParameterizedTest(name = "fibonacci({0})=={1}")
    @MethodSource("fibNumbers")
    fun `fiboMethodSourceTest`(n: Int, fib: Int) {
        assertEquals(fibonacci(n), fib)
    }
}