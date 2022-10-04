package com.example.exception.global.exception

open class CustomException : RuntimeException() {
    val errorCode: ErrorCode
        get() = ErrorCode.findByClass(this::class)
}