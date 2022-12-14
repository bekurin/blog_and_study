package chapter2

import chapter2.Listing1.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Listing1Test {

    @Test
    fun `purchase_successds_when_enuough_inventory`() {
        //given
        val store = Store()
        store.addInventory(Product.SHAMPOO, 10)
        val customer = Customer()

        //when
        val success = customer.purchase(store, Product.SHAMPOO, 5)

        //then
        assertTrue(success)
        assertEquals(5, store.getInventory(Product.SHAMPOO))
    }

    @Test
    fun `purchase_fails_when_not_enough_inventory`() {
        //given
        val store = Store()
        store.addInventory(Product.SHAMPOO, 10)
        val customer = Customer()

        //when
        val success = customer.purchase(store, Product.SHAMPOO, 15)

        //then
        assertFalse(success)
        assertEquals(10, store.getInventory(Product.SHAMPOO))
    }
}