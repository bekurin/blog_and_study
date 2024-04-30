package core.paymentservice.exception

import core.paymentservice.util.LocalizedMessageSource
import core.paymentservice.util.MessageSourceCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val localizedMessageSource: LocalizedMessageSource
) {
    @ExceptionHandler(HttpException::class)
    fun handleHttpException(httpException: HttpException): ResponseEntity<ExceptionResponse> {
        val responseStatus = httpException::class.annotations.firstOrNull { annotation -> annotation is ResponseStatus } as? ResponseStatus
        val message = localizedMessageSource.getMessage(httpException.messageSourceCode, httpException.getArguments())
        return creteResponseEntity(responseStatus?.code, message)
    }

    private fun creteResponseEntity(httpStatus: HttpStatus?, message: String?): ResponseEntity<ExceptionResponse> {
        val localizedMessage = message ?: localizedMessageSource.getMessage(MessageSourceCode.UNKNOWN_ERROR)
        return ResponseEntity
            .status(httpStatus ?: HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ExceptionResponse(localizedMessage))
    }
}
