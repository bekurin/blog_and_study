package chapter01.listing1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DonutShopKtTest {

    @Test
    fun testBuyCoffee() {
        val creditCard = CreditCard()
        buyDonut(creditCard)
        buyDonut(creditCard)
        assertEquals(Donut.price * 2, creditCard.total)
    }
}