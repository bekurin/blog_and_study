package com.example.exception.article

import com.example.exception.article.exception.ArticleNotFoundException
import com.example.exception.article.exception.ArticleWithSameTitleException
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

fun ArticleService.isValidTitleOrThrow(title: String) {
    val article = articles.find { it.title == title }
    if (article != null) {
        throw ArticleWithSameTitleException()
    }
}

@Service
class ArticleService {
    var articles: MutableList<Article> = ArrayList<Article>();

    @PostConstruct
    fun buildArticles() {
        articles.add(Article(1, "Exception handling in kotlin."))
        articles.add(Article(2, "Basic concept of kotlin."))
        articles.add(Article(3, "Tdd with spring in kotlin."))
    }

    fun create(title: String): Article {
        isValidTitleOrThrow(title)
        val createArticle = Article(articles.size + 1, title)
        articles.add(createArticle)
        return createArticle
    }

    fun update(id: Int, title: String): Article {
        isValidTitleOrThrow(title)
        val article = findById(id)
        article.title = title
        return article
    }

    fun findById(id: Int): Article {
        return articles.find { article -> article.id == id }
                ?: throw ArticleNotFoundException()
    }
}