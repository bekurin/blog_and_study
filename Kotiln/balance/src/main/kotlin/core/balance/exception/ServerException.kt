package core.balance.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class ServerException(
    message: String
) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(message: String) : ServerException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(message: String) : ServerException(message)

