package core.paymentservice.controller.payment.response

import core.paymentservice.domain.PaymentEvent

data class CheckoutResponse(
    val amount: Long,
    val orderKey: String,
    val orderName: String,
) {
    constructor(paymentEvent: PaymentEvent) : this(
        amount = paymentEvent.totalAmount(),
        orderKey = paymentEvent.orderKey,
        orderName = paymentEvent.orderName,
    )
}
