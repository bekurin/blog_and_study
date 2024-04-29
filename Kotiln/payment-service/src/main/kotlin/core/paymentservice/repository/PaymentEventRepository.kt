package core.paymentservice.repository

import core.paymentservice.domain.PaymentEvent
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PaymentEventRepository : ReactiveCrudRepository<PaymentEvent, Long> {
    fun findByOrderId(orderId: String): Mono<PaymentEvent>
}
