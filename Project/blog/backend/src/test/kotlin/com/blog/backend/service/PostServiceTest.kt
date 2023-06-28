package com.blog.backend.service

import com.blog.backend.controller.dto.CreatePostDto
import com.blog.backend.controller.dto.PostDto
import com.blog.backend.entity.Post
import com.blog.backend.exception.ResourceNotFoundException
import com.blog.backend.repository.PostRepository
import com.blog.backend.support.FakeSubject.PostSubject
import com.blog.backend.support.UnitTestBase
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
            private val givenPosts = (1..5).map { PostSubject.of(title = "$it") }
            private val givenPageable = PageRequest.of(0, 20, Sort.Direction.DESC, "id")

            @Test
            fun `페이징 처리된 글을 반환한다`() {
                // given
                given(postRepository.findPagedPost(givenPageable, null))
                        .willReturn(PageImpl(givenPosts, givenPageable, givenPosts.size.toLong()))

                // when
                val findPagedPost = sut.findPagedPost(givenPageable.pageNumber, givenPageable.pageSize, null)

                // then
                assertSoftly { softly ->
                    softly.assertThat(findPagedPost.currentPage).isEqualTo(givenPageable.pageNumber)
                    softly.assertThat(findPagedPost.nextPage).isEqualTo(givenPageable.pageNumber + 1)
                    softly.assertThat(findPagedPost.totalCount).isEqualTo(givenPosts.size.toLong())
                    softly.assertThat(findPagedPost.hasNext).isFalse
                    softly.assertThat(findPagedPost.hasPrevious).isFalse
                    softly.assertThat(findPagedPost.contents).containsExactlyInAnyOrderElementsOf(
                            givenPosts.map { PostDto(it) }
                    )
                }
            }
        }
    }

    @Nested
    inner class `글을 삭제할 때` {

        @Nested
        inner class `ID에 해당하는 글이 등록되어 있다면` {
            private val givenPost = PostSubject.of()
            private val postCaptor: ArgumentCaptor<Post> = ArgumentCaptor.forClass(Post::class.java)

            @Test
            fun `글을 삭제한다`() {
                // given
                given(postRepository.findById(givenPost.id))
                        .willReturn(Optional.of(givenPost))
                willDoNothing().given(postRepository).delete(any(Post::class.java))

                // when & then
                assertDoesNotThrow {
                    sut.deleteById(givenPost.id)
                }
            }
        }

        @Nested
        inner class `ID에 해당하는 글이 없다면` {

            @Test
            fun `리소스 없음 에러가 발생한다`() {
                // when & then
                assertThrows<ResourceNotFoundException> {
                    sut.deleteById(100)
                }

            }
        }
    }
}
