package crud.kafka

import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.http.ResponseEntity
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
    ): ResponseEntity<RecordMetadataResponse> {
        val recordMetadata = kafkaProducerService.sendMessage(message)
        return ResponseEntity.ok()
            .body(RecordMetadataResponse(recordMetadata))
    }
}

data class RecordMetadataResponse(
    val offset: Long,
    val timestamp: Long,
    val hasOffset: Boolean,
    val hasTimestamp: Boolean,
    val serializedKeySize: Int,
    val serializedValueSize: Int,
    val partition: Int
) {
    constructor(recordMetaData: RecordMetadata) : this(
        offset = recordMetaData.offset(),
        timestamp = recordMetaData.timestamp(),
        hasOffset = recordMetaData.hasOffset(),
        hasTimestamp = recordMetaData.hasTimestamp(),
        serializedKeySize = recordMetaData.serializedKeySize(),
        serializedValueSize = recordMetaData.serializedValueSize(),
        partition = recordMetaData.partition()
    )
}
