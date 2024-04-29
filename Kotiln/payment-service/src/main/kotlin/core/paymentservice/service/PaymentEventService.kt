package core.paymentservice.service

import core.paymentservice.domain.PaymentEvent
import core.paymentservice.repository.PaymentEventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

@Service
class PaymentEventService(
    private val paymentEventRepository: PaymentEventRepository,
    private val transactionalOperator: TransactionalOperator,
) {
    fun updatePaymentKeyByOrderId(orderId: String, paymentKey: String): Mono<PaymentEvent> {
        return paymentEventRepository.findByOrderId(orderId)
            .map { foundPaymentEvent -> foundPaymentEvent.updatePaymentKey(paymentKey) }
            .`as`(transactionalOperator::transactional)
    }

    fun updatePaymentStatus(): Mono<Boolean> {
        return Mono.just(false)
    }
}
