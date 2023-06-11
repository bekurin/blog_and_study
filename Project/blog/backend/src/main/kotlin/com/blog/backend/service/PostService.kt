package com.blog.backend.service

import com.blog.backend.controller.dto.CreatePostDto
import com.blog.backend.controller.dto.PageDto
import com.blog.backend.controller.dto.PostDto
import com.blog.backend.exception.ClientBadRequestException
import com.blog.backend.repository.PostRepository
import com.blog.backend.repository.findByIdOrThrow
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
) {
    fun findPagedPost(page: Int, size: Int, title: String?): PageDto<PostDto> {
        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        return PageDto(
            postRepository.findPagedPost(pageable, title)
                .map { PostDto(it) }
        )
    }

    fun findPostById(id: Long): PostDto {
        val findPost = postRepository.findByIdOrThrow(id)
        return PostDto(findPost)
    }

    @Transactional
    fun deleteById(id: Long) {
        val findPost = postRepository.findByIdOrThrow(id)
        postRepository.delete(findPost)
    }

    @Transactional
    fun createPost(dto: CreatePostDto): PostDto {
        postRepository.findByTitle(dto.title)
            .ifPresent {
                throw ClientBadRequestException("이미 저장된 글입니다")
            }
        val post = dto.toEntity()
        return PostDto(postRepository.save(post))
    }
}
