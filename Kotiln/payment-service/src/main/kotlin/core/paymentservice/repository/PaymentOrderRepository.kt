package core.paymentservice.repository

import core.paymentservice.domain.PaymentOrder
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface PaymentOrderRepository : ReactiveCrudRepository<PaymentOrder, Long> {
    fun findByOrderId(orderId: String): Flux<PaymentOrder>
}
