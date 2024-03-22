package com.example.onetimepassword

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OneTimePasswordApplication

fun main(args: Array<String>) {
	runApplication<OneTimePasswordApplication>(*args)
}
