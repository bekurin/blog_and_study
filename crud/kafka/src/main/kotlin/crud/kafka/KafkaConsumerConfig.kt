package crud.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.web.client.HttpServerErrorException.InternalServerError


@Configuration
class KafkaConsumerConfig {
    private val log = LoggerFactory.getLogger(KafkaConsumerConfig::class.java)

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        config[ConsumerConfig.GROUP_ID_CONFIG] = "myGroup"
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        factory.setCommonErrorHandler(handlerError())
        return factory
    }

    fun handlerError(): DefaultErrorHandler {
        val defaultErrorHandler = DefaultErrorHandler { consumerRecord, exception ->
            log.error("${consumerRecord.topic()} 처리 중에 예외가 발생했습니다.", exception)
        }
        defaultErrorHandler.addRetryableExceptions(InternalServerError::class.java)
        return defaultErrorHandler
    }
}
