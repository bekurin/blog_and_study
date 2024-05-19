package com.example.stock.config

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers


@Testcontainers
@SpringBootTest
@ExtendWith(
    MysqlContainerExtension::class,
    RedisContainerExtension::class
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
open class RepositoryTestBase {
}
