package com.blog.backend.repository

import com.blog.backend.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostRepository: JpaRepository<Post, Long> {
    fun findByTitle(title: String): Optional<Post>
}
