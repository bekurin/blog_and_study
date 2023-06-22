package com.blog.backend.controller

import com.blog.backend.controller.dto.CommentDto
import com.blog.backend.controller.dto.CreateCommentDto
import com.blog.backend.controller.dto.PageDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody

interface CommentControllerSpec {
    @Operation(summary = "댓글 리스트 조회", description = "글 ID에 등록되어 있는 댓글을 리스트로 조회한다.")
    @Parameters(
        Parameter(name = "page", description = "페이지 번호", required = true),
        Parameter(name = "size", description = "페이지 아이템 개수", required = true),
        Parameter(name = "postId", description = "글 ID", required = true),
    )
    fun findPagedCommentByPostId(postId: Long, page: Int, size: Int): PageDto<CommentDto>

    @Operation(summary = "댓글 생성", description = "작성자, 댓글 내용을 입력 받아 댓글을 생성한다.")
    @RequestBody(
        description = "댓글 생성 요청", required = true, content = [
            Content(schema = Schema(implementation = CreateCommentDto::class))
        ]
    )
    fun createComment(dto: CreateCommentDto): CommentDto

    @Operation(summary = "댓글 삭제", description = "댓글 ID를 사용하여 댓글을 삭제한다.")
    @Parameter(name = "id", description = "댓글 ID", required = true)
    fun deleteById(id: Long)
}
