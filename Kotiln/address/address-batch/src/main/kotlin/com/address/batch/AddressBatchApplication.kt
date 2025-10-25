package com.address.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AddressBatchApplication {
}

fun main(args: Array<String>) {
    runApplication<AddressBatchApplication>(*args)
}