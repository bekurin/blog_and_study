package core.kotlinredis.service

import core.kotlinredis.repository.Member
import core.kotlinredis.repository.Point
import core.kotlinredis.repository.RankingService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RankingServiceNestedTest constructor(
    @Autowired
    private val sut: RankingService,
) {

    @Nested
    @DisplayName("save()는 ")
    inner class Save() {
        private val entity = Member("1", "Bob", Point(123.0))

        @AfterEach
        fun cleanUp() {
            sut.delete(entity)
        }

        @Test
        fun `성공적으로 저장하면 저장된 회원을 반환한다`() {
            //given
            //when
            val save = sut.save(entity)

            //then
            assertEquals(entity, save)
        }

        @Test
        fun `저장에 실패하면 예외를 반환한다`() {
            //given
            sut.save(entity)

            //when
            //then
            assertThrows(RuntimeException::class.java) { sut.save(entity) }
        }
    }

    @Nested
    @DisplayName("update()는 ")
    inner class Update {
        private val entity = Member("1", "Bob", Point(123.0))
        private val expected = Member("1", "Bob", Point(1234.0))

        @BeforeEach
        fun preWork() {
            sut.save(entity)
        }

        @AfterEach
        fun cleanUp() {
            sut.delete(expected)
        }

        @Test
        fun `기존 회원을 삭제한다`() {
            //given
            //when
            val updated = sut.update(entity, 1234.0)

            //then
            assertThrows(RuntimeException::class.java) { sut.findByEntity(entity) }
        }

        @Test
        fun `수정된 회원을 저장한다`() {
            //given
            //when
            val updated = sut.update(entity, 1234.0)

            //then
            assertEquals(expected, updated)
        }
    }

    @Nested
    @DisplayName("delete()는 ")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class Delete {
        private val entity = Member("1", "Bob", Point(123.0))

        @BeforeAll
        fun preWork() {
            sut.save(entity)
        }

        @Test
        fun `회원을 삭제한다`() {
            //when
            val delete = sut.delete(entity)

            //then
            assertEquals(1L, delete)
        }

        @Test
        fun `회원을 삭제하지 못하면 예외를 반환한다`() {
            //given
            val entity = Member("2", "Bob", Point(12365.0))

            //when
            //then
            assertThrows(RuntimeException::class.java) { sut.delete(entity) }
        }
    }
}