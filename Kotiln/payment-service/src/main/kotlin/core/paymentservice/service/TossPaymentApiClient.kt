package core.paymentservice.service

import core.paymentservice.configuration.property.TossPaymentProperty
import core.paymentservice.service.dto.PaymentConfirmCommand
import core.paymentservice.service.dto.PaymentExecutionResult
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class TossPaymentApiClient(
    private val tossPaymentWebClient: WebClient,
    private val tossPaymentProperty: TossPaymentProperty,
) {
    fun confirm(command: PaymentConfirmCommand): Mono<PaymentExecutionResult> {
        return tossPaymentWebClient
            .post()
            .uri(tossPaymentProperty.confirm)
            .bodyValue(command)
            .retrieve()
            .bodyToMono(PaymentExecutionResult::class.java)
    }
}
