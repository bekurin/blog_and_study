package crud.kafka

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@Configuration
class KafkaProducerConfig {
    @Bean
    fun kafkaBaseEventTemplate(factory: ProducerFactory<String, BaseEventDto>): KafkaTemplate<String, BaseEventDto> {
        return KafkaTemplate(factory)
    }
}
