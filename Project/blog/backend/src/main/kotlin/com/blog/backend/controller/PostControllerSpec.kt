package com.blog.backend.controller

import com.blog.backend.controller.dto.CreatePostDto
import com.blog.backend.controller.dto.PageDto
import com.blog.backend.controller.dto.PostDto
import com.blog.backend.exception.ErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType

interface PostControllerSpec {
    @Operation(summary = "글 리스트 조회", description = "모든 글 리스트 조회한다.")
    @ApiResponses(
        value = [
            ApiResponse(description = "글 리스트 조회", responseCode = "200"),
            ApiResponse(
                description = "유효하지 않은 파라미터", responseCode = "400", content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )
                ]
            ),
        ]
    )
    fun findPagedPost(page: Int, size: Int, title: String?): PageDto<PostDto>

    @Operation(summary = "글 단건 조회", description = "글 ID를 사용하여 단건 조회한다.")
    @ApiResponses(
        value = [
            ApiResponse(description = "글 단건 조회", responseCode = "200"),
            ApiResponse(
                description = "유효하지 않은 파라미터", responseCode = "400", content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )
                ]
            ),
        ]
    )
    fun findPostById(id: Long): PostDto

    @Operation(summary = "글 생성", description = "글 제목, 내용을 입력받아 글을 생성한다.")
    @ApiResponses(
        value = [
            ApiResponse(description = "글 생성", responseCode = "201"),
            ApiResponse(
                description = "유효하지 않은 파라미터", responseCode = "400", content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )
                ]
            ),
        ]
    )
    fun createPost(request: CreatePostDto): PostDto

    @Operation(summary = "글 삭제", description = "글 ID를 사용하여 글을 삭제한다.")
    @ApiResponses(
        value = [
            ApiResponse(description = "글 삭제", responseCode = "204"),
            ApiResponse(
                description = "유효하지 않은 파라미터", responseCode = "400", content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )
                ]
            ),
        ]
    )
    fun deleteById(id: Long)
}
