package core.paymentservice.domain

import core.paymentservice.domain.PaymentStatus.FAILURE
import core.paymentservice.domain.PaymentStatus.SUCCESS
import core.paymentservice.domain.PaymentStatus.UNKNOWN
import org.springframework.data.relational.core.mapping.Table

@Table("payment_order")
class PaymentOrder(
    sellerId: Long,
    productId: Long,
    orderId: String,
    amount: Long,
    paymentStatus: PaymentStatus,
    isLedgerUpdated: Boolean = false,
    isWalletUpdated: Boolean = false,
) : BaseEntity() {
    var paymentEventId: Long = 0
        private set

    var sellerId: Long = sellerId
        private set

    var productId: Long = productId
        private set

    var orderId: String = orderId
        private set

    var amount: Long = amount
        private set

    var paymentStatus: PaymentStatus = paymentStatus
        private set

    var isLedgerUpdated: Boolean = isLedgerUpdated
        private set

    var isWalletUpdated: Boolean = isWalletUpdated
        private set

    fun updatePaymentEventId(paymentEventId: Long): PaymentOrder {
        this.paymentEventId = paymentEventId
        return this
    }

    fun updatePaymentStatus(paymentStatus: PaymentStatus): PaymentOrder {
        this.paymentStatus = paymentStatus
        return this
    }

    fun isSuccess(): Boolean {
        return paymentStatus == SUCCESS
    }

    fun isFailure(): Boolean {
        return paymentStatus == FAILURE
    }

    fun isUnknown(): Boolean {
        return paymentStatus == UNKNOWN
    }
}

enum class PaymentStatus(description: String) {
    NOT_STARTED("결제 처리 시작 전"),
    EXECUTING("결제 처리 중"),
    SUCCESS("결제 처리 성공"),
    FAILURE("결제 처리 실패"),
    UNKNOWN("알 수 없는 결제 상태")
}
