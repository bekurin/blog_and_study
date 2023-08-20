package core.cucumber

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CucumberApplication

fun main(args: Array<String>) {
    runApplication<CucumberApplication>(*args)
}
