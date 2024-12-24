package core.securitydebugging

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecurityDebuggingApplication

fun main(args: Array<String>) {
    runApplication<SecurityDebuggingApplication>(*args)
}
