package com.blog.backend.controller

import com.blog.backend.controller.dto.CommentDto
import com.blog.backend.controller.dto.CreateCommentDto
import com.blog.backend.controller.dto.PageDto
import io.swagger.v3.oas.annotations.Operation

interface CommentControllerSpec {
    @Operation(summary = "댓글 리스트 조회", description = "글 ID에 등록되어 있는 댓글을 리스트로 조회한다.")
    fun findPagedCommentByPostId(postId: Long, page: Int, size: Int): PageDto<CommentDto>

    @Operation(summary = "댓글 생성", description = "작성자, 댓글 내용을 입력 받아 댓글을 생성한다.")
    fun createComment(dto: CreateCommentDto): CommentDto

    @Operation(summary = "댓글 삭제", description = "댓글 ID를 사용하여 댓글을 삭제한다.")
    fun deleteById(id: Long)
}
