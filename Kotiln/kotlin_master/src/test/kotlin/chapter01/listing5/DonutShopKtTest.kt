package chapter01.listing5

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

internal class DonutShopKtTest {

    @Test
    fun `여러 카드로 도넛을 계산할 수 있다`() {
        //given
        val creditCard1 = CreditCard()
        val creditCard2 = CreditCard()

        //when
        val purchases = listOf(
            buyDonuts(1, creditCard1),
            buyDonuts(2, creditCard2),
            buyDonuts(3, creditCard1),
            buyDonuts(4, creditCard2),
            buyDonuts(5, creditCard1),
        )
        val creditCardAmountMap = Payment
            .groupByCard(purchases.map { it.payment })
            .fold(mutableMapOf<CreditCard, Int>()) { result, payment ->
                result[payment.creditCard] = payment.amount
                result
            }

        //then
        assertAll(
            { assertEquals(creditCardAmountMap[creditCard1], 45) },
            { assertEquals(creditCardAmountMap[creditCard2], 30) }
        )
    }
}