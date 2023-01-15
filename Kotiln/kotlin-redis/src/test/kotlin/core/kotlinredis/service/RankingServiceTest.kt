package core.kotlinredis.service

import core.kotlinredis.repository.Member
import core.kotlinredis.repository.Point
import core.kotlinredis.repository.RankingService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("redis 통합 테스트 하나의 클래스에 모든 케이스 구현")
internal class RankingServiceTest constructor(
    @Autowired
    val sut: RankingService,
) {

    @Test
    fun `save()는 성공적으로 저장하면 저장된 회원을 반환한다`() {
        //given
        val entity = Member("3", "Bob", Point(123.0))

        //when
        val save = sut.save(entity)

        //then
        assertEquals(entity, save)
    }

    @Test
    fun `save()는 저장에 실패하면 예외를 반환한다`() {
        //given
        val entity = Member("4", "Bob", Point(123.0))
        sut.save(entity)

        //when
        //then
        assertThrows(RuntimeException::class.java) { sut.save(entity) }
    }

    @Test
    fun `update()는 기존 회원을 삭제한다`() {
        //given
        val entity = Member("1", "Bob", Point(123.0))

        //when
        val updated = sut.update(entity, 1234.0)

        //then
        assertThrows(RuntimeException::class.java) { sut.findByEntity(entity) }
    }

    @Test
    fun `update()는 수정된 회원을 저장한다`() {
        //given
        val entity = Member("1", "Bob", Point(123.0))

        //when
        val updated = sut.update(entity, 1234.0)

        //then
        assertEquals(entity.update(point = Point(1234.0)), updated)
    }

    @Test
    fun `delete()는 회원을 삭제한다`() {
        //given
        val entity = Member("1", "Bob", Point(123.0))
        sut.save(entity)

        //when
        val delete = sut.delete(entity)

        //then
        assertEquals(1L, delete)
    }

    @Test
    fun `delete()는 회원을 삭제하지 못하면 예외를 반환한다`() {
        //given
        val entity = Member("1", "Bob", Point(12365.0))

        //when
        //then
        assertThrows(RuntimeException::class.java) { sut.delete(entity) }
    }
}




















































