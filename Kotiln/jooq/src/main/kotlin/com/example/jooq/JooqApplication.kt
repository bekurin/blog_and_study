package com.example.jooq

import org.jooq.DSLContext
import org.jooq.generated.tables.OrderItem
import org.jooq.generated.tables.Product
import org.jooq.generated.tables.records.OrderRecord
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.domain.Sort.Order

@SpringBootApplication
class JooqApplication(
    private val dslContext: DSLContext
) {
    fun test() {
    }
}

fun main(args: Array<String>) {
    runApplication<JooqApplication>(*args)
}
