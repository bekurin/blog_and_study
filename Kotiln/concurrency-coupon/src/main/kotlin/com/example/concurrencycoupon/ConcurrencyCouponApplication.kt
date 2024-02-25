package com.example.concurrencycoupon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConcurrencyCouponApplication

fun main(args: Array<String>) {
    runApplication<ConcurrencyCouponApplication>(*args)
}
