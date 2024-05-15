package com.example.stock.domain

import jakarta.persistence.Entity

@Entity
class Stock(
    productId: Long,
    quantity: Long
) : BaseEntity() {

    var productId: Long = productId
        protected set

    var quantity: Long = quantity
        protected set

    fun decrease(quantity: Long) {
        if (this.quantity - quantity <= 0) {
            throw RuntimeException()
        }
        this.quantity -= quantity
    }
}
