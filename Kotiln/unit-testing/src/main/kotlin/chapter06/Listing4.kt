package chapter06

import chapter06.Listing4.Article
import java.time.LocalDateTime

class Listing4 {
    class Article(
        val comments: MutableList<Comment> = mutableListOf(),
    ) {
        fun addComment(text: String, author: String, now: LocalDateTime) {
            comments.add(Comment(text, author, now))
        }
    }

    data class Comment(
        val text: String,
        val author: String,
        val createdAt: LocalDateTime,
    )
}

fun Article.shouldContainNumberOfComments(commentCount: Int): Article {
    assert(this.comments.size > 0)
    return this
}

fun Article.withComment(text: String, author: String, createdAt: LocalDateTime): Article {
    val comment = this.comments
        .asSequence()
        .filter { data -> data.equals(Listing4.Comment(text, author, createdAt)) }
        .firstOrNull() ?: throw RuntimeException("comment를 찾을 수 없습니다")
    return this
}