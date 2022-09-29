package com.example.exception

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExceptionApplication

fun main(args: Array<String>) {
	runApplication<ExceptionApplication>(*args)
}
