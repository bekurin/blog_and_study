package core.paymentservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class HttpException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ClientBadRequestException(message: String) : HttpException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(message: String) : HttpException(message)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerError(message: String) : HttpException(message)

@ResponseStatus(HttpStatus.CONFLICT)
class ResourceConflictException(message: String) : HttpException(message)
