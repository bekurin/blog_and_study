package core.paymentservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpException::class)
    fun handleHttpException(httpException: HttpException): ResponseEntity<ExceptionResponse> {
        val responseStatus =
            httpException::class.annotations.firstOrNull { annotation -> annotation is ResponseStatus } as? ResponseStatus
        return ResponseEntity
            .status(responseStatus?.value ?: HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ExceptionResponse(httpException.message))
    }
}
