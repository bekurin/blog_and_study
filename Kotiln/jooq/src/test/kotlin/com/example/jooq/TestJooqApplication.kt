package com.example.jooq

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestJooqApplication {

	@Bean
	@ServiceConnection
	fun mysqlContainer(): MySQLContainer<*> {
		return MySQLContainer(DockerImageName.parse("mysql:latest"))
	}

}

fun main(args: Array<String>) {
	fromApplication<JooqApplication>().with(TestJooqApplication::class).run(*args)
}
