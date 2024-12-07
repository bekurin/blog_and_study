package core.balance.util.message

import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*

@Service
class LocalizedMessageSource(
    private val messageSource: MessageSource
) {
    fun getMessage(code: MessageSourceCode, vararg args: Any): String {
        return messageSource.getMessage(code.path, args, Locale.getDefault())
    }
}
