package core.paymentservice.service.dto

import core.paymentservice.domain.PaymentEvent
import core.paymentservice.domain.PaymentOrder
import core.paymentservice.domain.PaymentStatus.NOT_STARTED
import core.paymentservice.domain.Product

data class CheckoutCommand(
    val cartId: Long,
    val buyerId: Long,
    val productIds: List<Long>,
    val idempotencyKey: String
) {
    fun toPaymentEvent(products: List<Product>): PaymentEvent {
        val paymentOrders = products.map { product ->
            PaymentOrder(
                sellerId = product.sellerId,
                orderId = idempotencyKey,
                productId = product.id,
                amount = product.amount,
                paymentStatus = NOT_STARTED
            )
        }
        return PaymentEvent(
            buyerId = buyerId,
            orderId = idempotencyKey,
            orderName = products.joinToString { product -> product.name },
        )
            .addPaymentOrders(paymentOrders)
    }
}
