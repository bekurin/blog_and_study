package com.example.hello.config

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@ExtendWith(
    MysqlContainerExtension::class,
    RedisContainerExtension::class,
)
class IntegrationTestBase
