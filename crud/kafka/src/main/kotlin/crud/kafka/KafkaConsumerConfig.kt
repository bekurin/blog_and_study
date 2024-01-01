package crud.kafka

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.util.backoff.FixedBackOff
import org.springframework.web.client.HttpServerErrorException.InternalServerError


@Configuration
class KafkaConsumerConfig(
    private val consumerFactory: ConsumerFactory<String, String>
) {
    private val log = LoggerFactory.getLogger(KafkaConsumerConfig::class.java)

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory
        factory.setCommonErrorHandler(handlerError())
        return factory
    }

    fun handlerError(): DefaultErrorHandler {
        val defaultErrorHandler = DefaultErrorHandler(
            { consumerRecord, exception ->
                log.error("${consumerRecord.topic()} 처리 중에 예외가 발생했습니다.", exception)
            },
            FixedBackOff(1000L, 1)
        )
        defaultErrorHandler.addRetryableExceptions(InternalServerError::class.java)
        return defaultErrorHandler
    }
}
