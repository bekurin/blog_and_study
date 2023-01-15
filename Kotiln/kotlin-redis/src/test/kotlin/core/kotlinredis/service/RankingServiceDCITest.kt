package core.kotlinredis.service

import core.kotlinredis.repository.Member
import core.kotlinredis.repository.Point
import core.kotlinredis.repository.RankingService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RankingServiceDCITest constructor(
    @Autowired
    private val sut: RankingService,
) {

    fun subject(id: String = "1", nickname: String = "Bob", point: Point = Point(123.0)): Member {
        return Member(id, nickname, point)
    }

    @Nested
    @DisplayName("save()는 ")
    inner class DescribeSave {
        private val entity = subject()

        @AfterEach
        fun cleanUp() {
            sut.delete(entity)
        }

        @Nested
        @DisplayName("성공적으로 저장하면 ")
        inner class ContextSaveSuccess {
            @Test
            fun `저장된 회원을 반환한다`() {
                val save = sut.save(entity)
                assertEquals(entity, save)
            }
        }

        @Nested
        @DisplayName("저장에 실패하면 ")
        inner class ContextSaveFail {
            @Test
            fun `예외를 반환한다`() {
                sut.save(entity)
                assertThrows(RuntimeException::class.java) { sut.save(entity) }
            }
        }
    }

    @Nested
    @DisplayName("save()는 ")
    inner class Save() {
        private val entity = subject()

        @AfterEach
        fun cleanUp() {
            sut.delete(entity)
        }

        @Test
        fun `성공적으로 저장하면 저장된 회원을 반환한다`() {
            val save = sut.save(entity)
            assertEquals(entity, save)
        }

        @Test
        fun `저장에 실패하면 예외를 반환한다`() {
            sut.save(entity)
            assertThrows(RuntimeException::class.java) { sut.save(entity) }
        }
    }
}