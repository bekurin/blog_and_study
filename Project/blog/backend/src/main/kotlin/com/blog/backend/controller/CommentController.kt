package com.blog.backend.controller

import com.blog.backend.controller.dto.CommentDto
import com.blog.backend.controller.dto.CreateCommentDto
import com.blog.backend.controller.dto.PageDto
import com.blog.backend.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class CommentController(
    private val commentService: CommentService,
) {

    @GetMapping("/comments/by-post-id/{postId}/page/{page}")
    fun findPagedCommentByPostId(
        @PathVariable postId: Long,
        @PathVariable page: Int,
        @RequestParam size: Int,
    ): PageDto<CommentDto> {
        return commentService.findPagedCommentByPostId(page, size, postId)
    }

    @PostMapping("/comments")
    fun createComment(
        @RequestBody dto: CreateCommentDto,
    ): CommentDto {
        return commentService.createComment(dto)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comments/{id}")
    fun deleteById(
        @PathVariable id: Long,
    ) {
        commentService.deleteCommentById(id)
    }

}
