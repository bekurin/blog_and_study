package enterprise.login.security.token

import org.springframework.http.HttpStatus

class OAuthException(
    val error: String,
    val errorDescription: String? = null,
    val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
) : RuntimeException(error)
