package chapter3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CustomerTest {

    @Test
    fun `고객 메시지에 접근하기`() {
        val customer = Customer("Fred").apply { messages }
        assertEquals(3, customer.messages.size)
    }

    @Test
    fun `고객 메시지 lazy 동작 확인`() {
        val customer = CustomerLazy("Fred")
        assertEquals(3, customer.messages.size)
    }
}