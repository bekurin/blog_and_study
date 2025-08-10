package stock.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockBatchApplication {
}

fun main(args: Array<String>) {
    runApplication<StockBatchApplication>(*args)
}