package core.paymentservice.controller.dto

import core.paymentservice.service.dto.CheckoutCommand

data class CheckoutCreationRequest(
    val cartId: Long,
    val buyerId: Long,
    val productIds: List<Long>,
    val idempotencyKey: String,
) {
    fun toCheckoutCommand(): CheckoutCommand {
        return CheckoutCommand(cartId, buyerId, productIds, idempotencyKey)
    }
}
