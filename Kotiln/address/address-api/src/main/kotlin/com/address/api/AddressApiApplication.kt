package com.address.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AddressApiApplication

fun main(args: Array<String>) {
    runApplication<AddressApiApplication>(*args)
}