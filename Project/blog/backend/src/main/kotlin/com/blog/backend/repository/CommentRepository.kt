package com.blog.backend.repository

import com.blog.backend.entity.Comment
import com.blog.backend.entity.Post
import com.blog.backend.exception.ResourceNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

fun CommentRepository.findByIdOrThrow(id: Long): Comment {
    return findById(id)
        .orElseThrow { throw ResourceNotFoundException("댓글이 존재하지 않습니다 (id=$id)") }
}

interface CommentRepository : JpaRepository<Comment, Long> {

    fun findAllByPost(post: Post, pageable: Pageable): Page<Comment>
}
