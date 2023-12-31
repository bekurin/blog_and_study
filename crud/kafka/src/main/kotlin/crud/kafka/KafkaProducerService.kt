package crud.kafka

import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.TimeUnit

@Service
class KafkaProducerService(
    private val kafkaBaseEventTemplate: KafkaTemplate<String, BaseEventDto>
) {
    fun sendMessage(message: String): RecordMetadata {
        val baseEventDto = BaseEventDto(UUID.randomUUID().toString(), message)
        return kafkaBaseEventTemplate.send(Constant.TOPIC, baseEventDto)
            .get(60, TimeUnit.SECONDS)
            .recordMetadata
    }
}
