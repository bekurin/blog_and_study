package com.example.exception.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    fun handlerCustomException(ex: CustomException): ResponseEntity<ErrorResponse> {
        return ErrorResponse.toResponseEntity(ex.errorCode)
    }
}