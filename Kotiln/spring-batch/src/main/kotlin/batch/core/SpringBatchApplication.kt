package batch.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SpringBatchApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchApplication>(*args)
}
