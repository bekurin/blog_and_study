package core.paymentservice.service.dto

import core.paymentservice.domain.PSPConfirmationStatus
import core.paymentservice.domain.PaymentFailure
import core.paymentservice.domain.PaymentMethod
import core.paymentservice.domain.PaymentStatus
import core.paymentservice.domain.PaymentType
import core.paymentservice.exception.ClientBadRequestException
import core.paymentservice.util.MessageSourceCode.INVALID_PAYMENT_EXECUTION
import java.time.LocalDateTime

data class PaymentExecutionResult(
    val paymentKey: String,
    val orderId: String,
    val extraDetails: PaymentExtraDetails? = null,
    val failure: PaymentFailure? = null,
    val isSuccess: Boolean,
    val isFailure: Boolean,
    val isUnknown: Boolean,
    val isRetryable: Boolean,
) {
    init {
        if (isSuccess || isFailure || isUnknown) {
            throw ClientBadRequestException(INVALID_PAYMENT_EXECUTION, orderId)
        }
    }

    fun getPaymentStatus(): PaymentStatus {
        return when {
            isSuccess -> PaymentStatus.SUCCESS
            isFailure -> PaymentStatus.FAILURE
            isUnknown -> PaymentStatus.UNKNOWN
            else -> throw ClientBadRequestException(INVALID_PAYMENT_EXECUTION, orderId)
        }
    }
}

data class PaymentExtraDetails(
    val type: PaymentType,
    val method: PaymentMethod,
    val approvedAt: LocalDateTime,
    val orderName: String,
    val pspConfirmationStatus: PSPConfirmationStatus,
    val totalAmount: Long,
    val pspRawData: String
)
