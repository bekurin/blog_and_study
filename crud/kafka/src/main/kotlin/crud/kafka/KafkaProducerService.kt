package crud.kafka

import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class KafkaProducerService(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun sendMessage(message: String): RecordMetadata {
        return kafkaTemplate.send(Constant.TOPIC, message)
            .get(60, TimeUnit.SECONDS)
            .recordMetadata
    }
}
