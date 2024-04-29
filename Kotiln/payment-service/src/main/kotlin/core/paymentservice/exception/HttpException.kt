package core.paymentservice.exception

import core.paymentservice.util.MessageSourceCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class HttpException(val messageSourceCode: MessageSourceCode, vararg args: Any) :
    RuntimeException(messageSourceCode.name) {
    private val arguments: Array<out Any> = args

    fun getArguments(): Array<out Any> {
        return arguments
    }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ClientBadRequestException(messageSourceCode: MessageSourceCode, vararg args: Any) :
    HttpException(messageSourceCode, args)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(messageSourceCode: MessageSourceCode, vararg args: Any) :
    HttpException(messageSourceCode, args)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerError(messageSourceCode: MessageSourceCode, vararg args: Any) :
    HttpException(messageSourceCode, args)
