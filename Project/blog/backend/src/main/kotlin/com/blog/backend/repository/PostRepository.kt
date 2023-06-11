package com.blog.backend.repository

import com.blog.backend.entity.Post
import com.blog.backend.entity.QPost.post
import com.blog.backend.exception.ClientBadRequestException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

fun PostRepository.findByIdOrThrow(id: Long): Post {
    return findById(id)
        .orElseThrow { ClientBadRequestException("글을 찾을 수 없습니다. (id=$id)") }
}

interface PostSearchRepository {
    fun findPagedPost(pageable: Pageable, title: String?): Page<Post>
}

interface PostRepository : JpaRepository<Post, Long>, PostSearchRepository {
    fun findByTitleLike(title: String, pageable: Pageable): Page<Post>

    fun findByTitle(title: String): Optional<Post>
}

class PostSearchRepositoryImpl : Querydsl4RepositorySupport(Post::class.java), PostSearchRepository {
    override fun findPagedPost(pageable: Pageable, title: String?): Page<Post> {
        val query = from(post)
            .where(
                title.let { post.title.like("$title%") }
            )
        return getPaginatedResultsAsPage(query, pageable)
    }

}
