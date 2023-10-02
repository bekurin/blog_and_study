package com.example.settingproject

import org.junit.ClassRule
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File

@Configuration
class TestContainersConfig {
    companion object {
        @ClassRule
        val environment = DockerComposeContainer(File("./docker-compose.yml"))
            .withExposedService("db", 3306, Wait.forListeningPort())

        init {
            environment.start()
            System.setProperty(
                "spring.datasource.hikari.jdbc-url",
                "jdbc:mysql://${
                    environment.getServiceHost("db", 3306)
                }:${environment.getServicePort("db", 3306)}/core"
            )
        }
    }
}
