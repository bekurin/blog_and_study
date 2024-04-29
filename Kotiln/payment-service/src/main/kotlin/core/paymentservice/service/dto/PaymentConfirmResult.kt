package core.paymentservice.service.dto

import core.paymentservice.domain.PaymentFailure
import core.paymentservice.domain.PaymentStatus

data class PaymentConfirmResult(
    val status: PaymentStatus,
    val failure: PaymentFailure? = null
)
