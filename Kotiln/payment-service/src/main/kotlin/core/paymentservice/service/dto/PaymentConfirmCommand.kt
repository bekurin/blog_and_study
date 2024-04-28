package core.paymentservice.service.dto

data class PaymentConfirmCommand(
    val paymentKey: String,
    val orderId: String,
    val amount: Long
)
