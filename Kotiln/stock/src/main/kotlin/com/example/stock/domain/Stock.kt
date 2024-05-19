package com.example.stock.domain

import com.example.stock.exception.ClientBadRequestException
import com.example.stock.support.ErrorCode.NOT_ENOUGH_QUANTITY
import jakarta.persistence.Entity
import jakarta.persistence.Version
import org.hibernate.annotations.DynamicUpdate

@Entity
@DynamicUpdate
class Stock(
    productId: Long,
    quantity: Long
) : BaseEntity() {

    var productId: Long = productId
        protected set

    var quantity: Long = quantity
        protected set

    @Version
    private val version: Long = 0L

    fun decrease(quantity: Long): Stock {
        if (this.quantity - quantity < 0) {
            throw ClientBadRequestException(NOT_ENOUGH_QUANTITY)
        }
        this.quantity -= quantity
        return this
    }
}
