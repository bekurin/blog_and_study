package com.blog.backend.entity

import com.blog.backend.repository.PostRepository
import com.blog.backend.support.FakeSubject.PostSubject
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest(showSql = true)
class PrimaryKeyEntityTest {

    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun `저장할 때 사용한 객체와 저장한 객체를 찾아서 비교했을 때 참을 반환한다`() {
        // given
        val givenPost = PostSubject.of()
        postRepository.save(givenPost)

        // when
        val findPost = postRepository.findById(givenPost.id).get()

        // then
        assertThat(givenPost).isEqualTo(findPost)
    }

    @Test
    fun `저장된 객체를 찾아서 필드값을 변경하면 update 쿼리가 실행된다`() {
        // given
        val givenPost = PostSubject.of()
        postRepository.save(givenPost)

        // when
        val findPost = postRepository.findById(givenPost.id).get()
        findPost.update("홍홍홍", givenPost.description)
        entityManager.flush()

        // then
        val updatePost = postRepository.findById(givenPost.id).get()
        assertThat(updatePost.title).isEqualTo(findPost.title)
    }
}
