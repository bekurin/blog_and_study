package chapter03

import chapter03.FluentAssertions1.Calculator
import org.junit.jupiter.api.Test
import kotlin.test.expect

internal class FluentAssertions1Test {

    @Test
    fun `sum_of_two_numbers`() {
        //given
        val first = 10.0
        val second = 20.0
        val sut = Calculator()

        //when
        //then
        expect(30.0) { sut.sum(first, second) }
    }
}