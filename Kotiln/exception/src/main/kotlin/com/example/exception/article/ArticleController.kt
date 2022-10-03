package com.example.exception.article

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/articles")
class ArticleController(
        val articleService: ArticleService,
) {

    @PostMapping()
    fun create(@RequestBody dto: ArticleRequestDto): ArticleResponseDto {
        return ArticleResponseDto(articleService.create(dto.title))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ArticleResponseDto {
        return ArticleResponseDto(articleService.findById(id))
    }

    @PutMapping("/{id}")
    fun update(
            @PathVariable id: Int,
            @RequestBody dto: ArticleUpdateRequestDto,
    ): ArticleResponseDto {
        return ArticleResponseDto(articleService.update(id, dto.title))
    }
}