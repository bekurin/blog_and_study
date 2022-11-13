package chapter3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

internal class LateInitDemoTest {
    @Test
    fun `초기화 되지 않은 lateinit 프로퍼티는 예외를 던진다`() {
        assertThrows<UninitializedPropertyAccessException> {
            LateInitDemo().name
        }
    }

    @Test
    fun `lateinit 프로퍼티 초기화 후 예외는 발생하지 않는다`() {
        assertDoesNotThrow { LateInitDemo().apply { name = "Dolly" } }
    }
}