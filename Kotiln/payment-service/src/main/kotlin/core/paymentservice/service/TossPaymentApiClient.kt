package core.paymentservice.service

import core.paymentservice.configuration.property.TossPaymentProperty
import core.paymentservice.controller.payment.request.TossPaymentConfirmRequest
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class TossPaymentApiClient(
    private val tossPaymentWebClient: WebClient,
    private val tossPaymentProperty: TossPaymentProperty,
) {
    fun confirm(request: TossPaymentConfirmRequest): Mono<String> {
        return tossPaymentWebClient
            .post()
            .uri(tossPaymentProperty.confirm)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String::class.java)
    }
}
