package com.blog.backend.service

import com.blog.backend.controller.dto.CommentDto
import com.blog.backend.controller.dto.CreateCommentDto
import com.blog.backend.entity.Comment
import com.blog.backend.exception.ResourceNotFoundException
import com.blog.backend.repository.CommentRepository
import com.blog.backend.repository.PostRepository
import com.blog.backend.support.FakeSubject.CommentSubject
import com.blog.backend.support.FakeSubject.PostSubject
import com.blog.backend.support.UnitTestBase
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
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
                // given
                given(postRepository.findById(givenCreateComment.postId))
                    .willReturn(Optional.of(givenPost))
                given(commentRepository.save(any(Comment::class.java)))
                    .willReturn(
                        CommentSubject.of(
                            givenCreateComment.author, givenCreateComment.description, givenPost
                        )
                    )

                // when
                val actual = sut.createComment(givenCreateComment)

                // then
                val expect = CommentDto(givenCreateComment.toEntity(givenPost))
                assertSoftly { softly ->
                    softly.assertThat(actual.author).isEqualTo(expect.author)
                    softly.assertThat(actual.description).isEqualTo(expect.description)
                }
            }
        }
    }

    @Nested
    inner class `댓글 삭제를 할 때` {

        @Nested
        inner class `ID에 해당하는 댓글이 없다면` {
            @Test
            fun `리소스 없음 에러가 발생한다`() {
                assertThrows<ResourceNotFoundException> {
                    sut.deleteCommentById(100)
                }
            }
        }

        @Nested
        inner class `ID에 해당하는 댓글이 있다면` {
            private val givenComment = CommentSubject.of(post = PostSubject.of())

            @Test
            fun `댓글을 삭제한다`() {
                // given
                given(commentRepository.findById(givenComment.id))
                    .willReturn(Optional.of(givenComment))

                // when & throw
                assertDoesNotThrow {
                    sut.deleteCommentById(givenComment.id)
                }
            }
        }
    }
}
