package core.paymentservice.controller.payment.request

import jakarta.validation.constraints.NotBlank

data class TossPaymentConfirmRequest(
    @get:NotBlank
    val paymentKey: String,
    @get:NotBlank
    val orderId: String,
    @get:NotBlank
    val amount: String
)
