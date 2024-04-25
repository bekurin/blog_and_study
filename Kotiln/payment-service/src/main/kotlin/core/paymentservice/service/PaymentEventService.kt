package core.paymentservice.service

import core.paymentservice.domain.PaymentEvent
import core.paymentservice.domain.PaymentOrder
import core.paymentservice.repository.PaymentEventRepository
import core.paymentservice.repository.PaymentOrderRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class PaymentEventService(
    private val paymentEventRepository: PaymentEventRepository,
    private val paymentOrderRepository: PaymentOrderRepository,
) {
    fun findOrderEventById(id: Int): Mono<PaymentEvent> {
        return paymentEventRepository.findById(id)
            .flatMap { paymentEvent ->
                paymentOrderRepository.findByPaymentEventId(paymentEvent.id)
                    .collectList()
                    .map { paymentOrders ->
                        paymentEvent.addPaymentOrders(paymentOrders)
                        paymentEvent
                    }
            }
    }
}
