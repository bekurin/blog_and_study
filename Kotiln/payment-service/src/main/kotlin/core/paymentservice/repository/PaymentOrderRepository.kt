package core.paymentservice.repository

import core.paymentservice.domain.PaymentOrder
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface PaymentOrderRepository : ReactiveCrudRepository<PaymentOrder, Int> {
    fun findByPaymentEventIdIn(paymentEventIdIn: List<Int>): Flux<PaymentOrder>

    fun findByPaymentEventId(paymentEventId: Int): Flux<PaymentOrder>
}
