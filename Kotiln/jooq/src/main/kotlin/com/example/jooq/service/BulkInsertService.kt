package com.example.jooq.service

import org.jooq.DSLContext
import org.jooq.generated.tables.records.ProductRecord

class BulkInsertService(
    private val dslContext: DSLContext
) {
    fun bulkInsert() {
        val productRecords = (1..100L).map { id ->
            ProductRecord().apply {
                this.id = id
                this.name = "test$id"
                this.quantity = id.toInt()
            }
        }
        dslContext.batchInsert(productRecords)
    }
}
