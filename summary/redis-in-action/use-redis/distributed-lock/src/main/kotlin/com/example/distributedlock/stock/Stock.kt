package com.example.distributedlock.stock

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
open class Stock(
    quantity: Int,
) {
    constructor() : this(0) {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0
        protected set

    var quantity: Int = quantity
        protected set

    fun decreaseQuantity(quantity: Int): Stock {
        if (quantity < 1) {
            throw IllegalArgumentException("수량은 음수가 될 수 없습니다.")
        }
        this.quantity -= quantity
        return this
    }

    fun increaseQuantity(quantity: Int): Stock {
        this.quantity += quantity
        return this
    }
}
