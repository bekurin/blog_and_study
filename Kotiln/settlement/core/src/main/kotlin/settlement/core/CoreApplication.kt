package settlement.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CoreApplication

fun main(args: Array<String>) {
    runApplication<CoreApplication>(*args)
}
