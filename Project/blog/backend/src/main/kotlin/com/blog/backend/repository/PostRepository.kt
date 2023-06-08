package com.blog.backend.repository

import com.blog.backend.entity.Post
import com.blog.backend.exception.ClientBadRequestException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

fun PostRepository.findByIdOrThrow(id: Long): Post {
    return findById(id)
        .orElseThrow { ClientBadRequestException("글을 찾을 수 없습니다. (id=$id)") }
}

interface PostRepository : JpaRepository<Post, Long> {
    fun findByTitleLike(title: String, pageable: Pageable): Page<Post>

    fun findByTitle(title: String): Optional<Post>
}
