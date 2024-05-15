package com.example.stock.support

import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocalizedMessageSource(
    private val messageSource: MessageSource
) {
    fun getMessage(errorCode: ErrorCode, vararg args: String): String {
        return messageSource.getMessage(errorCode.path, args, Locale.getDefault())
    }
}
