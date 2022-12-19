package chapter06

import chapter06.Listing2.Order
import chapter06.Listing2.Product
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Listing2Test {

    /**
     * 상태 기반 테스트로 출력 기반 테스트에 비해 리팩토링 내성이 약하다.
     * 기능을 수행한 후에 값을 검증하기 위해 코드의 세부 구현을 조금 알고 있어야한다.
     */

    @Test
    fun `adding_a_product_to_an_order`() {
        //given
        val product = Product("Hand Cream")
        val sut = Order()

        //when
        sut.addProduct(product)

        //then
        assertEquals(1, sut.products.size)
        assertEquals(product, sut.products[0])
    }
}