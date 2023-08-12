package com.example.validation.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class ExceptionHandler {

    /**
     * MethodArgumentNotValidException: body 검증에 문제가 발생한 경우 예외 발생
     * ConstraintViolationException: RequestParam or PathVariable 검증에 문제가 발생한 경우 예외 발생
     */
//    @ExceptionHandler(value = [MethodArgumentNotValidException::class, ConstraintViolationException::class])
//    fun handleNotValidException(exception: Exception): ResponseEntity<ErrorResponse> {
//        return ResponseEntity
//            .status(HttpStatus.BAD_REQUEST)
//            .body(ErrorResponse(getValidationMessageOrEmpty(exception)))
//    }

    @ExceptionHandler(value = [ValidatorException::class])
    fun handleNotValidException(exception: ValidatorException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.message?.let { ErrorResponse(it) })
    }

    /**
     * MethodArgumentTypeMismatchException: RequestParam or PathVariable 타입이 맞지 않은 경우 발생
     * HttpMessageNotReadableException: body 파라미터의 타입이 맞지 않은 경우 발생
     */
    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class, HttpMessageNotReadableException::class])
    fun handleTypeMismatchException(exception: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(exception.message ?: ""))
    }

    private fun getValidationMessageOrEmpty(exception: Exception): String {
        return when (exception) {
            is MethodArgumentNotValidException -> {
                exception.allErrors.map { it.defaultMessage }
            }

            is ConstraintViolationException -> {
                exception.constraintViolations.map { it.message }
            }

            else -> {
                listOf("")
            }
        }.joinToString(", ")
    }

}

data class ErrorResponse(
    val message: String
)
