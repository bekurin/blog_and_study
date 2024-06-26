package core.paymentservice.util

enum class MessageSourceCode(val path: String) {
    INVALID_PAYMENT_EXECUTION("error.invalidPaymentExecution"),
    UNKNOWN_ERROR("error.unknownError");
}
