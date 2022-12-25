package chapter01.listing3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DonutShopKtTest {

    @Test
    fun `여러 도넛을 구매하면 amount가 늘어난다`() {
        //given
        val creditCard = CreditCard()
        val purchase1 = buyDonut(creditCard)
        val purchase2 = buyDonut(creditCard)

        //when
        val combine = purchase1.payment.combine(purchase2.payment)

        //then
        assertEquals(combine.creditCard, creditCard)
        assertEquals(combine.amount, Donut.price * 2)
    }
}