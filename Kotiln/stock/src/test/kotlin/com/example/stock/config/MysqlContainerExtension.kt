package com.example.stock.config

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitStrategy
import org.testcontainers.junit.jupiter.Container

class MysqlContainerExtension : BeforeAllCallback {
    companion object {
        private const val MYSQL_VERSION = "mysql:8.0"
        private const val DATABASE_NAME = "stock"
        private const val USERNAME = "root"
        private const val PASSWORD = "1234"
        private const val EXPOSED_PORT = 3306

        @Container
        private val mysqlContainer = MySQLContainer(MYSQL_VERSION)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(USERNAME)
            .withPassword(PASSWORD)
            .withExposedPorts(EXPOSED_PORT)
            .withReuse(true)
            .waitingFor(Wait.forListeningPort())
    }

    override fun beforeAll(context: ExtensionContext?) {
        if (mysqlContainer.isRunning) {
            return
        }

        mysqlContainer.start()
        System.setProperty(
            "spring.datasource.hikari.jdbc-url", "jdbc:mysql://localhost:${
                mysqlContainer.getMappedPort(
                    EXPOSED_PORT
                )
            }/$DATABASE_NAME?useSSL=false&useUnicode=true&characterEncoding=UTF-8"
        )
        System.setProperty("spring.datasource.hikari.username", USERNAME)
        System.setProperty("spring.datasource.hikari.password", PASSWORD)

    }
}
