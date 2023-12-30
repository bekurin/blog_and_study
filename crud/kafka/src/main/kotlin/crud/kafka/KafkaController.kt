package crud.kafka

import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class KafkaController(
    private val kafkaProducerService: KafkaProducerService
) {
    @GetMapping("/send")
    fun send(
        @RequestParam message: String
    ): RecordMetadata {
        return kafkaProducerService.sendMessage(message)
    }
}
