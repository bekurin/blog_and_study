package core.paymentservice.controller.payment.response

import core.paymentservice.domain.PaymentEvent

data class CheckoutResponse(
    val amount: Long,
    val orderId: String,
    val orderName: String,
) {
    constructor(paymentEvent: PaymentEvent) : this(
        amount = paymentEvent.totalAmount(),
        orderId = paymentEvent.orderId,
        orderName = paymentEvent.orderName,
    )
}
