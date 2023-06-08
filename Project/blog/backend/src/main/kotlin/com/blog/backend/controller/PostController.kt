package com.blog.backend.controller

import com.blog.backend.controller.dto.CreatePostDto
import com.blog.backend.controller.dto.PageResponse
import com.blog.backend.controller.dto.PostDto
import com.blog.backend.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class PostController(
    private val postService: PostService,
) {

    @GetMapping("/posts/page/{page}")
    fun findPagedPost(
        @PathVariable page: Int,
        @RequestParam(defaultValue = "20") size: Int,
    ): PageResponse<PostDto> {
        return postService.findPagedPost(page, size)
    }

    @GetMapping("/posts/{id}")
    fun findPostById(
        @PathVariable id: Long,
    ): PostDto {
        return postService.findPostById(id)
    }

    @PostMapping("/posts")
    fun createPost(
        @RequestBody request: CreatePostDto,
    ): PostDto {
        return postService.createPost(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/posts/{id}")
    fun deleteById(
        @PathVariable id: Long,
    ) {
        return postService.deleteById(id)
    }

}
