package core.paymentservice.domain

import java.time.LocalDateTime

data class PaymentEvent(
    val buyerId: Int,
    val orderName: String,
    val orderKey: String,
    val paymentKey: String,
    val paymentType: PaymentType,
    val paymentMethod: PaymentMethod,
    val approvedAt: LocalDateTime,
    private var isPaymentDone: Boolean = false
) : BaseEntity() {
    fun isDone(): Boolean {
        return isPaymentDone
    }
}

enum class PaymentType(description: String) {
    NORMAL("일반 결제")
}

enum class PaymentMethod(description: String) {
    EASY_PAY("간편 결제");
}
