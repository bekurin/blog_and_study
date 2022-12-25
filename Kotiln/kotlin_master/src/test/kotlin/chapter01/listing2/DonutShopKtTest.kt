package chapter01.listing2

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DonutShopKtTest {

    @Test
    fun `결제 항목과 카드과 payment 와 같다`() {
        //given
        val creditCard = CreditCard()

        //when
        val result = buyDonut(creditCard)

        //then
        assertEquals(result.payment.creditCard, creditCard)
        assertEquals(result.payment.amount, Donut.price)
    }
}