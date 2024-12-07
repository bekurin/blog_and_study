package core.balance.util.exception

import core.balance.util.message.MessageSourceCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class ServerException : RuntimeException {
    var translatedMessage: String = ""
    var messageSourceCode: MessageSourceCode? = null
    var arguments: Array<out String> = emptyArray<String>()

    override val message: String?
        get() = translatedMessage.ifBlank { super.message }

    constructor(messageSourceCode: MessageSourceCode, vararg args: Any) : super(messageSourceCode.name) {
        this.messageSourceCode = messageSourceCode
        this.arguments = args.map { it.toString() }.toTypedArray()
    }

    constructor(message: String) : super(message)

    fun isLocalized(): Boolean {
        return messageSourceCode != null
    }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException : ServerException {
    constructor(messageSourceCode: MessageSourceCode, vararg args: String) : super(messageSourceCode, *args)
    constructor(message: String) : super(message)
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(messageSourceCode: MessageSourceCode, vararg args: Any) :
    ServerException(messageSourceCode, *args)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerException(messageSourceCode: MessageSourceCode, vararg args: Any) :
    ServerException(messageSourceCode, *args)
