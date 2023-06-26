package com.blog.backend.service

import com.blog.backend.controller.dto.CreatePostDto
import com.blog.backend.entity.Post
import com.blog.backend.repository.PostRepository
import com.blog.backend.support.FakeSubject.PostSubject
import com.blog.backend.support.UnitTestBase
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.times
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import java.util.*

class PostServiceTest : UnitTestBase() {

    @InjectMocks
    private lateinit var sut: PostService

    @Mock
    private lateinit var postRepository: PostRepository

    @Nested
    inner class `글을 등록할 때` {
        @Nested
        inner class `유효한 파라미터가 입력되면` {
            private val postCaptor: ArgumentCaptor<Post> = ArgumentCaptor.forClass(Post::class.java)
            private val givenCreatePostDto = CreatePostDto("제목", "설명")
            private val givenPost = PostSubject.of(title = givenCreatePostDto.title, description = givenCreatePostDto.description)

            @Test
            fun `글을 생성한다`() {
                // given
                given(postRepository.findByTitle(givenCreatePostDto.title))
                        .willReturn(Optional.empty())
                given(postRepository.save(any(Post::class.java)))
                        .willReturn(givenPost)

                // when
                sut.createPost(givenCreatePostDto)

                // then
                verify(postRepository, times(1)).save(postCaptor.capture())
                assertSoftly { softly ->
                    softly.assertThat(givenPost.title).isEqualTo(postCaptor.value.title)
                    softly.assertThat(givenPost.description).isEqualTo(postCaptor.value.description)
                }
            }
        }
    }

    @Nested
    inner class `글을 조회할 때` {
        @Nested
        inner class `유효한 파리미터가 입력되면` {

            @Test
            fun `페이징 처리된 글을 반환한다`() {

            }
        }
    }

    @Nested
    inner class `글을 삭제할 때` {

        @Nested
        inner class `ID에 해당하는 글이 등록되어 있다면` {

            @Test
            fun `글을 삭제한다`() {

            }
        }

        @Nested
        inner class `ID에 해당하는 글이 없다면` {

            @Test
            fun `리소스 없음 에러가 발생한다`() {

            }
        }
    }
}
