package com.blog.backend.service

import com.blog.backend.controller.dto.CommentDto
import com.blog.backend.controller.dto.CreateCommentDto
import com.blog.backend.controller.dto.PageDto
import com.blog.backend.repository.CommentRepository
import com.blog.backend.repository.PostRepository
import com.blog.backend.repository.findByIdOrThrow
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) {
    fun findPagedCommentByPostId(page: Int, size: Int, postId: Long): PageDto<CommentDto> {
        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        val findPost = postRepository.findByIdOrThrow(postId)
        return PageDto(
            commentRepository.findAllByPost(findPost, pageable)
                .map { CommentDto(it) }
        )
    }

    @Transactional
    fun createComment(request: CreateCommentDto): CommentDto {
        val findPost = postRepository.findByIdOrThrow(request.postId)
        val comment = request.toEntity(findPost)
        return CommentDto(commentRepository.save(comment))
    }

    @Transactional
    fun deleteCommentById(id: Long) {
        val findComment = commentRepository.findByIdOrThrow(id)
        commentRepository.delete(findComment)
    }
}
