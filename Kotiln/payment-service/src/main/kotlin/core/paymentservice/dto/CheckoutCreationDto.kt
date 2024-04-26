package core.paymentservice.dto

import core.paymentservice.domain.PaymentEvent
import core.paymentservice.domain.PaymentOrder
import core.paymentservice.domain.PaymentStatus.NOT_STARTED
import core.paymentservice.domain.Product

data class CheckoutCreationDto(
    val cartId: Int,
    val buyerId: Int,
    val productIds: List<Int>,
    val idempotencyKey: String,
) {
    fun toPaymentEvent(products: List<Product>): PaymentEvent {
        val paymentOrders = products.map { product ->
            PaymentOrder(
                sellerId = product.sellerId,
                orderKey = idempotencyKey,
                productId = product.id,
                amount = product.amount,
                paymentStatus = NOT_STARTED
            )
        }
        return PaymentEvent(
            buyerId = buyerId,
            orderKey = idempotencyKey,
            orderName = products.joinToString { product -> product.name },
        )
            .addPaymentOrders(paymentOrders)
    }
}
