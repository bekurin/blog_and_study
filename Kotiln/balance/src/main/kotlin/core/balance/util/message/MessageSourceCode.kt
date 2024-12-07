package core.balance.util.message

enum class MessageSourceCode(
    val path: String
) {
    UNKNOWN("error.unknown"),
    ACCOUNT_NOT_FOUND("error.account"),
    NEGATIVE_AMOUNT("error.negativeAmount"),
    NEGATIVE_BALANCE("error.negativeBalance"),
    INSUFFICIENT_BALANCE("error.insufficientBalance"),
    NOT_SAME_ACCOUNT("error.notSameAccount"),
    INVALID_VERSION("error.invalidVersion"),
}
