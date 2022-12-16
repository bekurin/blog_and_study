package chapter03

import chapter03.Listing1.Calculator
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Listing1Test {

    @Test
    fun `sum_of_two_numbers`() {
        //given
        val first: Double = 10.0
        val second: Double = 20.0
        val calculator = Calculator()

        //when
        val result = calculator.sum(first, second)

        //then
        assertEquals(result, 30.0)
    }
}