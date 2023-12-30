package crud.kafka

import crud.kafka.Constant.TOPIC
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService {
    @KafkaListener(topics = [TOPIC], groupId = "myGroup")
    fun listen(message: String) {
        println("Received Message: $message")
    }
}
