package com.example.exception.global.exception

import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

class ErrorResponse(
        val timestamp: LocalDateTime = LocalDateTime.now(),
        val status: Int,
        val error: String,
        val code: String,
        val message: String,
) {
    companion object {
        fun toResponseEntity(errorCode: ErrorCode): ResponseEntity<ErrorResponse> {
            return ResponseEntity
                    .status(errorCode.httpStatus)
                    .body(
                            ErrorResponse(status = errorCode.httpStatus.value(),
                                    error = errorCode.httpStatus.name,
                                    code = errorCode.name,
                                    message = errorCode.errorMessage)
                    );
        }
    }
}