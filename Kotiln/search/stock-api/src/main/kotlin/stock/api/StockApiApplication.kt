package stock.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockApiApplication {
}

fun main(args: Array<String>) {
    runApplication<StockApiApplication>(*args)
}
