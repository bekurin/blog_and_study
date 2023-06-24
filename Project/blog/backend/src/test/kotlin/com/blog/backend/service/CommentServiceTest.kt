package com.blog.backend.service

import com.blog.backend.controller.dto.CreateCommentDto
import com.blog.backend.repository.CommentRepository
import com.blog.backend.repository.PostRepository
import com.blog.backend.repository.findByIdOrThrow
import com.blog.backend.support.FakeSubject
import com.blog.backend.support.FakeSubject.CommentSubject
import com.blog.backend.support.FakeSubject.PostSubject
import com.blog.backend.support.UnitTestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock

class CommentServiceTest : UnitTestBase() {

    @InjectMocks
    private lateinit var sut: CommentService

    @Mock
    private lateinit var commentRepository: CommentRepository

    @Mock
    private lateinit var postRepository: PostRepository

    @Nested
    inner class `댓글 등록을 할 때` {

        @Nested
        inner class `유효한 파리미터가 입력되면` {
            private val givenPost = PostSubject.of()
            private val givenCreateComment = CreateCommentDto("홍길동", "댓글 내용", 1)

            @Test
            fun `댓글을 생성한다`() {
                given(postRepository.findByIdOrThrow(givenCreateComment.postId))
                    .willReturn(givenPost)
                given(commentRepository.save(givenCreateComment.toEntity(givenPost)))
                    .willReturn(CommentSubject.of(post = givenPost))

                val createComment = sut.createComment(givenCreateComment)
                assertThat(createComment).isEqualTo(givenCreateComment)
            }
        }
    }

    @Nested
    inner class `댓글 삭제를 할 때` {

    }
}
