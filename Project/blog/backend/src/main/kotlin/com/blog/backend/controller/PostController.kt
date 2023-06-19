package com.blog.backend.controller

import com.blog.backend.controller.dto.CreatePostDto
import com.blog.backend.controller.dto.PageDto
import com.blog.backend.controller.dto.PostDto
import com.blog.backend.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class PostController(
    private val postService: PostService,
) : PostControllerSpec {
    @GetMapping("/posts/page/{page}")
    override fun findPagedPost(
        @PathVariable page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(required = false) title: String?,
    ): PageDto<PostDto> {
        return postService.findPagedPost(page, size, title)
    }

    @GetMapping("/posts/{id}")
    override fun findPostById(
        @PathVariable id: Long,
    ): PostDto {
        return postService.findPostById(id)
    }

    @PostMapping("/posts")
    override fun createPost(
        @RequestBody request: CreatePostDto,
    ): PostDto {
        return postService.createPost(request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/posts/{id}")
    override fun deleteById(
        @PathVariable id: Long,
    ) {
        return postService.deleteById(id)
    }
}
