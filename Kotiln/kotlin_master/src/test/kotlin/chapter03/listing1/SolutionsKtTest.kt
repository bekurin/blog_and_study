package chapter03.listing1

import chapter03.square
import chapter03.triple
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SolutionsKtTest {

    @Test
    fun `정수 타입의 함수를 합성하여 사용할 수 있다`() {
        //given
        val n = 3
        val compose = exercise1(::square, ::triple)

        //when
        val result = compose(n)

        //then
        assertEquals(81, result)
    }

    @Test
    fun `모든 타입의 함수를 합성하여 사용할 수 있다`() {
        //given
        val n = 2.5
        val compose = exercise2<Double, Double, Double>(
            { data ->
                data * data
            }, { data ->
                data * 3
            })

        //when
        val result = compose(n)

        //then
        assertEquals(56.25, result)
    }

    @Test
    fun `Int 인자를 연쇄적으로 받아 인자를 더할 수 있다`() {
        //given
        val (a, b) = Pair(3, 5)

        //when
        val result = exercise3(a)(b)

        //then
        assertEquals(8, result)
    }

    @Test
    fun `함수 값을 사용하여 합성 함수로 만들 수 있다`() {
        //given
        val n = 5
        val square: IntUnaryOp = { it * it }
        val triple: IntUnaryOp = { it * 3 }

        //when
        val compose = exercise4V3(square)(triple)
        val result = compose(n)

        //then
        assertEquals(225, result)
    }

    @Test
    fun `함수 값을 사용한 합성 함수를 다형적으로 만들 수 있다`() {
        //given
        val n = 4.0
        val square: ((Double) -> Double) = { it * it }
        val triple: ((Double) -> Double) = { it * 3 }

        //when
        val compose = exercise5<Double, Double, Double>()(square)(triple)
        val result = compose(n)

        //then
        assertEquals(144.0, result)
    }

    @Test
    fun `함수 값을 사용한 합성 함수의 순서를 변경할 수 있다`() {
        //given
        val n = 4.0
        val square: ((Double) -> Double) = { it * it }
        val triple: ((Double) -> Double) = { it * 3 }

        //when
        val compose = exercise5<Double, Double, Double>()(triple)(square)
        val result = compose(n)

        //then
        assertEquals(48.0, result)
    }

    @Test
    fun `함수를 입력 받아 함수를 리턴할 수 있다`() {
        //given
        val f = { a: Int -> { b: Double -> a * (1 + b / 100) } }

        //when
        val result = exercise7(8, f)

        //then
        assertEquals(8.8, result(10.0))
    }
}