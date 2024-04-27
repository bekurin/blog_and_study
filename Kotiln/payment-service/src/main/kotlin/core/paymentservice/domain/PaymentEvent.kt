package core.paymentservice.domain

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("payment_event")
class PaymentEvent(
    buyerId: Int,
    orderName: String,
    orderId: String,
    paymentKey: String? = null,
    paymentType: PaymentType? = null,
    paymentMethod: PaymentMethod? = null,
    approvedAt: LocalDateTime? = null,
    isPaymentDone: Boolean = false,
) : BaseEntity() {
    var buyerId: Int = buyerId
        private set

    var orderName: String = orderName
        private set

    var orderId: String = orderId
        private set

    var paymentKey: String? = paymentKey
        private set

    var paymentType: PaymentType? = paymentType
        private set

    var paymentMethod: PaymentMethod? = paymentMethod
        private set

    var approvedAt: LocalDateTime? = approvedAt
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
        val updatedPaymentOrders = paymentOrders.map { paymentOrder -> paymentOrder.updatePaymentEventId(this.id) }
        this.paymentOrders.addAll(updatedPaymentOrders)
        return this
    }

    fun totalAmount(): Long {
        return paymentOrders.sumOf { it.amount }
    }

    fun isSuccess(): Boolean {
        return paymentOrders.all { paymentOrder -> paymentOrder.isSuccess() }
    }

    fun isFailure(): Boolean {
        return paymentOrders.any { paymentOrder -> paymentOrder.isFailure() }
    }

    fun isUnknown(): Boolean {
        return paymentOrders.any { paymentOrder -> paymentOrder.isUnknown() }
    }
}

enum class PaymentType(description: String) {
    NORMAL("일반 결제")
}

enum class PaymentMethod(description: String) {
    EASY_PAY("간편 결제");
}
