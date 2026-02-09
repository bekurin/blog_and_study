package core.serverdrivenui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServerDrivenUiApplication

fun main(args: Array<String>) {
    runApplication<ServerDrivenUiApplication>(*args)
}
