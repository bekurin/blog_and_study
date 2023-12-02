package core.jdbc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DatasourceApplication

fun main(args: Array<String>) {
    runApplication<DatasourceApplication>(*args)
}
