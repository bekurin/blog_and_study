package core.server_side_ui_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServerSideUiServerApplication

fun main(args: Array<String>) {
    runApplication<ServerSideUiServerApplication>(*args)
}
