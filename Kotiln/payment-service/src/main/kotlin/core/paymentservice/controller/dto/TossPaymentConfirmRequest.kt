package core.paymentservice.controller.dto

import core.paymentservice.service.dto.PaymentConfirmCommand
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class TossPaymentConfirmRequest(
    @get:NotBlank
    val paymentKey: String,
    @get:NotBlank
    val orderId: String,
    @get:Min(0)
    val amount: Long
) {
    fun toPaymentConfirmCommand(): PaymentConfirmCommand {
        return PaymentConfirmCommand(paymentKey, orderId, amount)
    }
}
