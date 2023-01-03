package core.chapter01

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class Chapter01Application {
    @GetMapping("/")
    fun helloWorld(): String {
        return "hello-world"
    }
}

fun main(args: Array<String>) {
    runApplication<Chapter01Application>(*args)
}
