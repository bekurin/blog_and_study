package chapter03

import chapter02.Listing2.*
import kotlin.test.*

internal class Customer3Test {

    private lateinit var store: Store
    private lateinit var sut: Customer

    @BeforeTest
    fun init() {
        store = StoreImpl()
        store.addInventory(Product.SHAMPOO, 10)
        sut = Customer()
    }


    @Test
    fun `purchase_succeeds_when_enough_inventory`() {
        //given
        //when
        val success = sut.purchase(store, Product.SHAMPOO, 5)

        //then
        assertTrue(success)
        assertEquals(5, store.getInventory(Product.SHAMPOO))
    }

    @Test
    fun `purchase_fails_when_not_enough_inventory`() {
        //given
        //when
        val success = sut.purchase(store, Product.SHAMPOO, 15)

        //then
        assertFalse(success)
        assertEquals(10, store.getInventory(Product.SHAMPOO))
    }
}