package core.paymentservice.configuration

import core.paymentservice.configuration.property.TossPaymentProperty
import core.paymentservice.util.Constant
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.util.*

@Configuration
class WebClientConfiguration(
    private val tossPaymentProperty: TossPaymentProperty
) {
    @Bean
    fun tossPaymentWebClient(): WebClient {
        val encodedSecretKey = Base64.getEncoder().encodeToString((tossPaymentProperty.secretKey + ":").toByteArray())
        return WebClient.builder()
            .baseUrl(tossPaymentProperty.baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic $encodedSecretKey")
            .clientConnector(reactorClientHttpConnector())
            .build()
    }

    private fun reactorClientHttpConnector(): ReactorClientHttpConnector {
        val provider = ConnectionProvider.builder(Constant.TOSS_PAYMENT)
            .build()
        return ReactorClientHttpConnector(HttpClient.create(provider))
    }
}
