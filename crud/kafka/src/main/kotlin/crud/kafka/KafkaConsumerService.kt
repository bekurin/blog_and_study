package crud.kafka

import crud.kafka.Constant.TOPIC
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService {
    private val log = LoggerFactory.getLogger(KafkaConsumerService::class.java)

    @KafkaListener(topics = [TOPIC], groupId = "crudKafka")
    fun listen(message: String) {
        // throw RuntimeException("예외 발생")
        log.info("Received Message: $message")
    }
}
