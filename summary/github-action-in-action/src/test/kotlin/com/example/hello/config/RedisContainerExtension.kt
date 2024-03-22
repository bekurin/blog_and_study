package com.example.hello.config

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container

class RedisContainerExtension : BeforeAllCallback {
    companion object {
        private const val REDIS_VERSION = "redis:7.0.7"
        private const val REDIS_PORT = 6379

        @Container
        private val redisContainer: GenericContainer<*> =
            GenericContainer(REDIS_VERSION)
                .withExposedPorts(REDIS_PORT)
    }

    override fun beforeAll(context: ExtensionContext?) {
        if (redisContainer.isRunning) {
            return
        }
        redisContainer.start()
        System.setProperty("spring.data.redis.host", redisContainer.host)
        System.setProperty("spring.data.redis.port", redisContainer.getMappedPort(REDIS_PORT).toString())
    }
}
