package core.paymentservice.controller.dto

import core.paymentservice.service.dto.CheckoutCommand
import core.paymentservice.util.IdempotencyCreator
import java.time.LocalDateTime

data class CheckoutCreationRequest(
    val cartId: Long = 1,
    val buyerId: Long = 1,
    val productIds: List<Long> = listOf(1, 2, 3, 4, 5),
    val seed: String = LocalDateTime.now().toString()
) {
    fun toCheckoutCommand(): CheckoutCommand {
        return CheckoutCommand(cartId, buyerId, productIds, IdempotencyCreator.create(seed))
    }
}
