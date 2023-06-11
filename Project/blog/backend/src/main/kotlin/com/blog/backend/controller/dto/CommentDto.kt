package com.blog.backend.controller.dto

import com.blog.backend.entity.Comment
import com.blog.backend.entity.Post

data class CommentDto(
    val author: String,
    val description: String,
) {
    constructor(entity: Comment) : this(
        author = entity.author,
        description = entity.description
    )
}

data class CreateCommentDto(
    val author: String,
    val description: String,
    val postId: Long,
) {
    fun toEntity(post: Post): Comment {
        return Comment(author, description, post)
    }
}
