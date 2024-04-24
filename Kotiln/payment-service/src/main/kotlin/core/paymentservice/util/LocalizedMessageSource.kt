package core.paymentservice.util

import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocalizedMessageSource(
    private val messageSource: MessageSource
) {
    fun getMessage(messageSourceCode: MessageSourceCode, vararg args: Any): String {
        val locale = Locale.getDefault() ?: Locale.KOREA
        return messageSource.getMessage(messageSourceCode.path, args, locale)
    }
}
