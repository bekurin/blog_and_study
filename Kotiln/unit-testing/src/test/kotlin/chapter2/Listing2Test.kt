package chapter2

import chapter2.Listing2.*
import org.mockito.Mockito.*
import org.mockito.internal.verification.Times
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Listing2Test {

    @Test
    fun `purchase_succeeds_when_enuogh_inventory`() {
        //given
        val storeMock = mock(Store::class.java)
        `when`(storeMock.hasEnoughInventory(Product.SHAMPOO, 5))
            .thenReturn(true)
        val customer = Customer()

        //when
        val success = customer.purchase(storeMock, Product.SHAMPOO, 5)

        //then
        assertTrue(success)
        verify(storeMock, Times(1)).removeInventory(Product.SHAMPOO, 5)
    }

    @Test
    fun `purchase_fails_when_not_enough_inventory`() {
        //given
        val storeMock = mock(Store::class.java)
        `when`(storeMock.hasEnoughInventory(Product.SHAMPOO, 5))
            .thenReturn(false)
        val customer = Customer()

        //when
        val success = customer.purchase(storeMock, Product.SHAMPOO, 5)

        //then
        assertFalse(success)
        verify(storeMock, never()).removeInventory(Product.SHAMPOO, 5)
    }
}