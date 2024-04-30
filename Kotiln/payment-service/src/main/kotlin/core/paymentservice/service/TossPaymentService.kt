package core.paymentservice.service

import core.paymentservice.domain.PaymentStatus.EXECUTING
import core.paymentservice.service.dto.PaymentConfirmCommand
import core.paymentservice.service.dto.PaymentConfirmResult
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

@Service
class TossPaymentService(
    private val tossPaymentApiClient: TossPaymentApiClient,
    private val paymentOrderService: PaymentOrderService,
    private val paymentEventService: PaymentEventService,
    private val transactionalOperator: TransactionalOperator
) {
    fun confirm(command: PaymentConfirmCommand): Mono<PaymentConfirmResult> {
        return paymentOrderService.updatePaymentOrderStatusByOrderId(orderId = command.orderId, paymentStatus = EXECUTING,)
            .flatMap { paymentEventService.updatePaymentKeyByOrderId(orderId = command.orderId, paymentKey = command.paymentKey) }
            .flatMap { tossPaymentApiClient.confirm(command) }
            .then(tossPaymentApiClient.confirm(command))
            .flatMap { paymentExecutionResult -> paymentEventService.updatePaymentStatus().thenReturn(paymentExecutionResult) }
            .map { paymentExecutionResult -> PaymentConfirmResult(status = paymentExecutionResult.getPaymentStatus(), failure = paymentExecutionResult.failure) }
            .`as`(transactionalOperator::transactional)
    }
}
