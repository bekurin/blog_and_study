package core.paymentservice.domain

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("payment_event")
class PaymentEvent(
    buyerId: Int,
    orderName: String,
    orderKey: String,
    paymentKey: String,
    paymentType: PaymentType,
    paymentMethod: PaymentMethod,
    approvedAt: LocalDateTime,
    isPaymentDone: Boolean = false,
) : BaseEntity() {
    var buyerId: Int = buyerId
        private set

    var orderName: String = orderName
        private set

    var orderKey: String = orderKey
        private set

    var paymentKey: String = paymentKey
        private set

    var paymentType: PaymentType = paymentType
        private set

    var paymentMethod: PaymentMethod = paymentMethod
        private set

    var approvedAt: LocalDateTime = approvedAt
        private set

    var isPaymentDone: Boolean = isPaymentDone
        private set

    @Transient
    var paymentOrders: MutableList<PaymentOrder> = mutableListOf()
        private set

    fun isDone(): Boolean {
        return isPaymentDone
    }

    fun addPaymentOrders(paymentOrders: List<PaymentOrder>): PaymentEvent {
        this.paymentOrders.addAll(paymentOrders)
        return this
    }

    fun totalAmount(): Long {
        return paymentOrders.sumOf { it.amount }
    }
}

enum class PaymentType(description: String) {
    NORMAL("일반 결제")
}

enum class PaymentMethod(description: String) {
    EASY_PAY("간편 결제");
}
