package chapter3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ProductTest {
    @Test
    fun `data class hashcode 같은지 테스트`() {
        val p1 = Product("baseball", 20000.0)
        val p2 = Product("baseball", 20000.0, false)

        assertEquals(p1, p2)
        assertEquals(p1.hashCode(), p2.hashCode())
    }

    @Test
    fun `집합 자료형을 사용하여 hashcode 가 같은지 확인`() {
        val p1 = Product("baseball", 20000.0)
        val p2 = Product("baseball", 20000.0, false)

        val products = setOf(p1, p2)
        assertEquals(1, products.size)
    }

    @Test
    fun `copy 를 사용하여 가격 바꾸기`() {
        val p1 = Product("baseball", 20000.0)
        val p2 = p1.copy(price = 25000.0)

        assertAll(
            { assertEquals("baseball", p2.name) },
            { assertFalse(p2.onSale) },
            { assertEquals(p2.price, 25000.0) },
        )
    }

    @Test
    fun `얕은 복사 검증 테스트`() {
        val item1 = OrderItem(Product("baseball", 20000.0), 8)
        val item2 = item1.copy()

        assertAll(
            { assertTrue(item1 == item2) },
            { assertFalse(item1 === item2) },
            { assertTrue(item1.product == item2.product) },
            { assertTrue(item1.product === item2.product) }
        )
    }

    @Test
    fun `Product 인스턴스 구조 분해`() {
        val p = Product("baseball", 15000.0)

        val (name, price, sale) = p
        assertAll(
            { assertEquals(p.name, name) },
            { assertEquals(p.price, price) },
            { assertFalse(sale) }


        )
    }
}
