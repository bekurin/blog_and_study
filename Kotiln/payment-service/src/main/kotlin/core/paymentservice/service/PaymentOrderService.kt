package core.paymentservice.service

import core.paymentservice.domain.PaymentOrder
import core.paymentservice.domain.PaymentStatus
import core.paymentservice.repository.PaymentOrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux

@Service
class PaymentOrderService(
    private val paymentOrderRepository: PaymentOrderRepository,
    private val transactionalOperator: TransactionalOperator,
) {
    fun updatePaymentOrderStatusByOrderId(orderId: String, paymentStatus: PaymentStatus): Flux<PaymentOrder> {
        return paymentOrderRepository.findByOrderId(orderId)
            .map { paymentOrder -> paymentOrder.updatePaymentStatus(paymentStatus) }
            .flatMap { updatedPaymentOrder -> paymentOrderRepository.save(updatedPaymentOrder) }
            .`as`(transactionalOperator::transactional)
    }
}
