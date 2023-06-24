package com.blog.backend.support

import com.blog.backend.entity.Comment
import com.blog.backend.entity.Post
import jakarta.persistence.EntityManager

object FakeSubject {
    object CommentSubject {
        fun of(
            author: String = "홍길동",
            description: String = "글 내용",
            post: Post,
        ): Comment {
            return Comment(author, description, post)
        }

        fun create(
            comment: Comment,
            entityManager: EntityManager,
        ): Comment {
            entityManager.persist(comment)
            return comment
        }
    }

    object PostSubject {
        fun of(
            title: String = "글 제목",
            description: String = "글 내용",
            comments: MutableSet<Comment> = mutableSetOf(),
        ): Post {
            return Post(title, description, comments)
        }

        fun create(
            post: Post,
            entityManager: EntityManager,
        ): Post {
            entityManager.persist(post)
            return post
        }
    }
}
