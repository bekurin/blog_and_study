package com.example.api.config

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.GenericContainer

class MysqlContainerExtension : BeforeAllCallback {
    companion object {
        val mysqlContainer = GenericContainer("mysql:8.0")
    }

    override fun beforeAll(context: ExtensionContext?) {
        TODO("Not yet implemented")
    }
}
