package com.example.hello.config

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container

class MysqlContainerExtension : BeforeAllCallback {
    companion object {
        private const val MYSQL_VERSION = "mysql:8.0"
        private const val DATABASE_NAME = "test"
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
        private const val MYSQL_PORT = 3306

        @Container
        private val mysqlContainer: MySQLContainer<*> =
            MySQLContainer(MYSQL_VERSION)
                .withDatabaseName(DATABASE_NAME)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withExposedPorts(MYSQL_PORT)
    }

    override fun beforeAll(context: ExtensionContext?) {
        if (mysqlContainer.isRunning) {
            return
        }
        mysqlContainer.start()
        System.setProperty(
            "spring.datasource.hikari.jdbc-url",
            "jdbc:mysql://localhost:${mysqlContainer.getMappedPort(MYSQL_PORT)}/$DATABASE_NAME",
        )
        System.setProperty("spring.datasource.hikari.username", USERNAME)
        System.setProperty("spring.datasource.hikari.password", PASSWORD)
        System.setProperty("spring.datasource.hikari.driver-class-name", "com.mysql.cj.jdbc.Driver")
    }
}
