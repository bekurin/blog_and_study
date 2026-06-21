package enterprise.login.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import enterprise.login.security.token.OAuthException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(OAuthException::class)
    fun handleOAuthException(ex: OAuthException): ResponseEntity<OAuthErrorResponse> =
        ResponseEntity
            .status(ex.httpStatus)
            .body(OAuthErrorResponse(error = ex.error, errorDescription = ex.errorDescription))

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException): ResponseEntity<ApiErrorResponse> =
        ResponseEntity
            .status(ex.statusCode)
            .body(ApiErrorResponse(status = ex.statusCode.value(), message = ex.reason ?: ex.message))
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OAuthErrorResponse(
    @JsonProperty("error") val error: String,
    @JsonProperty("error_description") val errorDescription: String?,
)

data class ApiErrorResponse(
    val status: Int,
    val message: String?,
)
