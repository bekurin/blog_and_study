package com.blog.backend.service

import com.blog.backend.controller.dto.CreatePostDto
import com.blog.backend.controller.dto.PostDto
import com.blog.backend.exception.ClientBadRequestException
import com.blog.backend.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
) {
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
