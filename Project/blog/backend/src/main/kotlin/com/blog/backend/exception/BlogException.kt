package com.blog.backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ClientBadRequestException(
    message: String
): HttpException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(
    message: String
): HttpException(message)
