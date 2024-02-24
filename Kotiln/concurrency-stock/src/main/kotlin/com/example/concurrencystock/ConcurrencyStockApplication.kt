package com.example.concurrencystock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConcurrencyStockApplication

fun main(args: Array<String>) {
	runApplication<ConcurrencyStockApplication>(*args)
}
