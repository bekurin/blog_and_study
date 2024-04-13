package core.jdbcclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JdbcClientApplication

fun main(args: Array<String>) {
    runApplication<JdbcClientApplication>(*args)
}
