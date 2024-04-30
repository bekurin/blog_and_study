package core.paymentservice.service

import core.paymentservice.domain.PaymentEvent
import core.paymentservice.repository.PaymentEventRepository
import core.paymentservice.repository.PaymentOrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

@Service
class SavePaymentService(
    private val paymentOrderRepository: PaymentOrderRepository,
    private val paymentEventRepository: PaymentEventRepository,
    private val transactionalOperator: TransactionalOperator
) {
    fun save(paymentEvent: PaymentEvent): Mono<PaymentEvent> {
        return paymentEventRepository.save(paymentEvent)
            .flatMap { savedPaymentEvent ->
                val updatedPaymentOrders = savedPaymentEvent.paymentOrders.map { it.updatePaymentEventId(savedPaymentEvent.id) }
                paymentOrderRepository.saveAll(updatedPaymentOrders)
                    .collectList()
                    .map { savedPaymentEvent }
            }
            .`as`(transactionalOperator::transactional)
    }
}
