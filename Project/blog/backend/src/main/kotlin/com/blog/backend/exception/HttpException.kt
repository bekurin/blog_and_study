package com.blog.backend.exception

open class HttpException(
    message: String? = null,
) : RuntimeException(message)
