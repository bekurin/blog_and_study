package core.balance.util.exception

import core.balance.util.message.LocalizedMessageSource
import core.balance.util.message.MessageSourceCode
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val localizedMessageSource: LocalizedMessageSource
) {
    @ExceptionHandler(ServerException::class)
    fun handleServerException(serverException: ServerException): ResponseEntity<ExceptionResponse> {
        val responseStatus =
            serverException::class.annotations.firstOrNull { annotation -> annotation is ResponseStatus } as? ResponseStatus
        if (serverException.isLocalized()) {
            val message =
                localizedMessageSource.getMessage(serverException.messageSourceCode!!, *serverException.arguments)
            serverException.translatedMessage = message
        }
        return createResponseEntity(responseStatus?.value, serverException.message)
    }

    private fun createResponseEntity(httpStatus: HttpStatus?, message: String?): ResponseEntity<ExceptionResponse> {
        val unknownErrorMessage = localizedMessageSource.getMessage(MessageSourceCode.UNKNOWN)

        return ResponseEntity
            .status(httpStatus ?: HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                ExceptionResponse(message = message ?: unknownErrorMessage),
            )
    }
}
