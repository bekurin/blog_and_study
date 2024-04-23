package core.paymentservice.controller.payment.request

import core.paymentservice.exception.ClientBadRequestException

data class TossPaymentConfirmRequest(
    val paymentKey: String,
    val orderId: String,
    val amount: String
) {
    init {
        if (orderId.isBlank() || paymentKey.isBlank() || amount.isBlank()) {
            throw ClientBadRequestException("")
        }
    }
}
