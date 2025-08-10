package stock.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockConsumerApplication {
}

fun main(args: Array<String>) {
    runApplication<StockConsumerApplication>(*args)
}