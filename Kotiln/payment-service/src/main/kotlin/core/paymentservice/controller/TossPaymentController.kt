package core.paymentservice.controller

import core.paymentservice.controller.dto.TossPaymentConfirmRequest
import core.paymentservice.service.TossPaymentApiClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1/toss")
class TossPaymentController(
    private val tossPaymentApiClient: TossPaymentApiClient
) {

    @PostMapping("/confirm")
    fun confirm(@RequestBody request: TossPaymentConfirmRequest): Mono<String> {
        return tossPaymentApiClient.confirm(request.toPaymentConfirmCommand())
    }
}
