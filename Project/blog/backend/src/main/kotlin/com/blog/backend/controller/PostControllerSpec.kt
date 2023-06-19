package com.blog.backend.controller

import com.blog.backend.controller.dto.CreatePostDto
import com.blog.backend.controller.dto.PageDto
import com.blog.backend.controller.dto.PostDto
import io.swagger.v3.oas.annotations.Operation

interface PostControllerSpec {
    @Operation(summary = "글 리스트 조회", description = "모든 글 리스트 조회한다.")
    fun findPagedPost(page: Int, size: Int, title: String?): PageDto<PostDto>

    @Operation(summary = "글 단건 조회", description = "글 ID를 사용하여 단건 조회한다.")
    fun findPostById(id: Long): PostDto

    @Operation(summary = "글 생성", description = "글 제목, 내용을 입력받아 글을 생성한다.")
    fun createPost(request: CreatePostDto): PostDto

    @Operation(summary = "글 삭제", description = "글 ID를 사용하여 글을 삭제한다.")
    fun deleteById(id: Long)
}
