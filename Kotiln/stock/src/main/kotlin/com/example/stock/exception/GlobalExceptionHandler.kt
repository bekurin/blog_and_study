package com.example.stock.exception

import com.example.stock.support.ErrorCode
import com.example.stock.support.LocalizedMessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val localizedMessageSource: LocalizedMessageSource
) {
    @ExceptionHandler(ServerException::class)
    fun handleServerException(serverException: ServerException): ResponseEntity<ErrorResponse> {
        val responseStatus =
            serverException::class.annotations.firstOrNull { annotation -> annotation is ResponseStatus } as? ResponseStatus
        val message = localizedMessageSource.getMessage(serverException.errorCode, *serverException.getArguments())
        return createErrorResponse(serverException, responseStatus?.code, message)
    }

    private fun createErrorResponse(
        serverException: ServerException,
        httpStatus: HttpStatus?,
        message: String?
    ): ResponseEntity<ErrorResponse> {
        val unknownError = localizedMessageSource.getMessage(ErrorCode.UNKNOWN_ERROR)
        val errorResponse = ErrorResponse.create(
            serverException,
            httpStatus ?: HttpStatus.INTERNAL_SERVER_ERROR,
            message ?: unknownError
        )
        return ResponseEntity
            .status(httpStatus ?: HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(errorResponse)
    }
}
