package core.paymentservice.configuration.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "toss-payment")
data class TossPaymentProperty(
    val baseUrl: String,
    val confirm: String,
    val secretKey: String
)
