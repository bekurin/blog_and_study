package com.example.api.config

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.GenericContainer

class RedisContainerExtension : BeforeAllCallback {

    companion object {
        val redisContainer = GenericContainer("redis:7.0")
    }

    override fun beforeAll(context: ExtensionContext?) {
        if (redisContainer.isRunning) {
            return
        }
        System.setProperty("spring.datasource.redis.host", "localhost")
        System.setProperty("spring.datasource.redis.port", "${redisContainer.getMappedPort(3306)}")

    }
}
