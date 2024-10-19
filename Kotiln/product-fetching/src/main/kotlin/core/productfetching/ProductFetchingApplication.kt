package core.productfetching

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductFetchingApplication

fun main(args: Array<String>) {
    runApplication<ProductFetchingApplication>(*args)
}
