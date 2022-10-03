package com.example.exception.article

data class ArticleResponseDto(
        val id: Int,
        val title: String,
) {
    constructor(article: Article) : this(
            id = article.id,
            title = article.title
    )
}

data class ArticleRequestDto(
        val title: String,
)

data class ArticleUpdateRequestDto(
        val title: String,
)