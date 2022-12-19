package chapter06

import chapter06.Listing1.PriceEngine
import chapter06.Listing1.Product
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Listing1Test {

    /**
     * 출력 기반 테스트를 리팩토링 내성이 좋다. 함수형 코딩의 계산에 해당된다.
     */

    @Test
    fun `discount_of_two_products`() {
        //given
        val product1 = Product("Hand Cream")
        val product2 = Product("Shampoo")
        val sut = PriceEngine()

        //when
        val discount = sut.calculateDiscount(listOf(product1, product2))

        //then
        assertEquals(discount, 0.02)
    }
}