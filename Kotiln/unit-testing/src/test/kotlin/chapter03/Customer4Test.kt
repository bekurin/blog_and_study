package chapter03

import chapter02.Listing2.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Customer4Test {

    @Test
    fun `purchase_succeeds_when_enough_inventory`() {
        //given
        val store = createStoreWithInventory(Product.SHAMPOO, 10)
        val sut = createCustomer()

        //when
        val success = sut.purchase(store, Product.SHAMPOO, 5)

        //then
        assertTrue(success)
        assertEquals(5, store.getInventory(Product.SHAMPOO))
    }

    @Test
    fun `purchase_fails_when_not_enough_inventory`() {
        //given
        val store = createStoreWithInventory(Product.SHAMPOO, 10)
        val sut = createCustomer()

        //when
        val success = sut.purchase(store, Product.SHAMPOO, 15)

        //then
        assertFalse(success)
        assertEquals(10, store.getInventory(Product.SHAMPOO))
    }

    private fun createStoreWithInventory(product: Product, quantity: Int): StoreImpl {
        val store = StoreImpl()
        store.addInventory(product, quantity)
        return store
    }

    private fun createCustomer(): Customer {
        return Customer()
    }
}