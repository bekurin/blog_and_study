package com.example.stock.exception

import com.example.stock.support.ErrorCode
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.ResponseStatus

open class ServerException(val errorCode: ErrorCode, vararg args: String) : RuntimeException() {
    private val arguments = args

    fun getArguments(): Array<out String> {
        return arguments
    }
}

@ResponseStatus(BAD_REQUEST)
class ClientBadRequestException(errorCode: ErrorCode, vararg args: String) : ServerException(errorCode, *args) {}
