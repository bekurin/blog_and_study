package core.r2dbch2.sender

import core.r2dbch2.utils.doGetAsMono
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class CustomerSender {
    private val webClient: WebClient = WebClient.create("http://localhost:8080")

    fun getBalanceBy(customerId: String): Mono<Double> {
        return webClient.doGetAsMono<Double>("/$customerId")
    }
}