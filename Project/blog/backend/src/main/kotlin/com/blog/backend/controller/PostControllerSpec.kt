package com.blog.backend.controller

import com.blog.backend.controller.dto.CreatePostDto
import com.blog.backend.controller.dto.PageDto
import com.blog.backend.controller.dto.PostDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

interface PostControllerSpec {
    @Operation(summary = "글 리스트 조회", description = "모든 글 리스트 조회한다.")
    @Parameters(
        Parameter(name = "page", description = "페이지 번호", required = true),
        Parameter(name = "size", description = "페이지 아이템 개수", required = true),
        Parameter(name = "title", description = "글 제목"),
    )
    @ApiResponses(
        value = [
            ApiResponse(description = "글 리스트 조회", responseCode = "200")
        ]
    )
    fun findPagedPost(page: Int, size: Int, title: String?): PageDto<PostDto>

    @Operation(summary = "글 단건 조회", description = "글 ID를 사용하여 단건 조회한다.")
    @Parameters(
        Parameter(name = "id", description = "글 ID", required = true),
    )
    @ApiResponses(
        value = [
            ApiResponse(description = "글 단건 조회", responseCode = "200"),
        ]
    )
    fun findPostById(id: Long): PostDto

    @Operation(summary = "글 생성", description = "글 제목, 내용을 입력받아 글을 생성한다.")
    @RequestBody(
        description = "글 생성 요청", required = true, content = [
            Content(schema = Schema(implementation = CreatePostDto::class))
        ]
    )
    @ApiResponses(
        value = [
            ApiResponse(description = "글 생성", responseCode = "201")
        ]
    )
    fun createPost(request: CreatePostDto): PostDto

    @Operation(summary = "글 삭제", description = "글 ID를 사용하여 글을 삭제한다.")
    @Parameter(name = "id", description = "글 ID", required = true)
    @ApiResponses(
        value = [
            ApiResponse(description = "글 삭제", responseCode = "204"),
        ]
    )
    fun deleteById(id: Long)
}
