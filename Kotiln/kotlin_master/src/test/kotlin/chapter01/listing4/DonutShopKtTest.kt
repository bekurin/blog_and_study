package chapter01.listing4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DonutShopKtTest {
    
    @Test
    fun `배열 도넛의 계산을 한번에 처리할 수 있다`() {
        //given
        val creditCard = CreditCard()

        //when
        val purchase = buyDonuts(5, creditCard)

        //then
        assertEquals(Donut.price * 5, purchase.payment.amount)
        assertEquals(creditCard, purchase.payment.creditCard)
    }
}