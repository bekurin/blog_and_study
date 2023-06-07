package com.blog.backend.controller.dto

import com.blog.backend.entity.Post

data class CreatePostDto(
    val title: String,
    val description: String,
) {
    fun toEntity(): Post {
        return Post(title, description)
    }
}

data class PostDto(
    val title: String,
) {
    constructor(entity: Post) : this(
        title = entity.title
    )
}
