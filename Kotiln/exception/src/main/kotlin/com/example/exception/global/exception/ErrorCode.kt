package com.example.exception.global.exception

import com.example.exception.article.exception.ArticleNotFoundException
import com.example.exception.article.exception.ArticleWithSameTitleException
import com.example.exception.global.exception.exception.ErrorNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import kotlin.reflect.KClass

enum class ErrorCode(
        val httpStatus: HttpStatus,
        val errorMessage: String,
        val klass: KClass<out CustomException>,
) {
    ERROR_NOT_FOUND(NOT_FOUND, "에러를 찾을 수 없습니다", ErrorNotFoundException::class),
    ARTICLE_NOT_FOUND(NOT_FOUND, "기사를 찾을 수 없습니다", ArticleNotFoundException::class),
    EXIST_ARTICLE(BAD_REQUEST, "해당 제목을 가진 기사가 이미 존재합니다", ArticleWithSameTitleException::class);

    companion object {
        fun findByClass(klass: KClass<out CustomException>): ErrorCode {
            return ErrorCode.values().first { it.klass == klass }
        }
    }
}