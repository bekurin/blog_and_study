package chapter05

import chapter05.Listing9.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.internal.verification.Times
import kotlin.test.assertTrue

internal class Listing9Test {

    @Test
    @Disabled(value = "concept illustration only")
    fun `"successful_purchase`() {
        //given
        val mock = mock(EmailGateway::class.java)
        val sut = CustomerController(mock)

        //when
        val success = sut.purchase(1, 2, 5)

        //then
        assertTrue(success)
        verify(mock, Times(1)).sendReceipt("customer@gmail.com", "Shampoo", 5)
    }

    @Test
    fun `purchase_succeeds_when_enough_inventory`() {
        //given
        val storeMock = mock(Store::class.java)
        `when`(storeMock.hasEnoughInventory(Product("Shampoo"), 5))
            .thenReturn(true)
        val customer = Customer()

        //when
        val success = customer.purchase(storeMock, Product("Shampoo"), 5)

        //then
        assertTrue(success)
        verify(storeMock, Times(1))
            .removeInventory(Product("Shampoo"), 5)
    }
}