package core.r2dbch2.config

import org.h2.tools.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import java.sql.SQLException

@Configuration
class H2ServerConfig {

    lateinit var webServer: Server

    @Value("\${spring.r2dbc.port}")
    lateinit var port: String

    @EventListener(ContextRefreshedEvent::class)
    @Throws(SQLException::class)
    fun start() {
        println("webServer Start!")
        webServer = Server.createWebServer("-webPort", port)
        webServer.start()
    }

    @EventListener(ContextClosedEvent::class)
    @Throws(SQLException::class)
    fun stop() {
        println("webServer Stopped!")
        webServer.stop()
    }

}