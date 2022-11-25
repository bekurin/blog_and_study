package core.kotlinredis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinRedisApplication

fun main(args: Array<String>) {
    runApplication<KotlinRedisApplication>(*args)
}
