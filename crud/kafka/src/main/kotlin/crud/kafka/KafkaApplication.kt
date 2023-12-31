package crud.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@SpringBootApplication
class KafkaApplication

fun main(args: Array<String>) {
    runApplication<KafkaApplication>(*args)
}
