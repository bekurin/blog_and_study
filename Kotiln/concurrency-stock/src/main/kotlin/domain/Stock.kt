package domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
open class Stock(
    productId: Long,
    quantity: Long
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0
        protected set

    var productId: Long = productId
        protected set

    var quantity: Long = quantity
        protected set

    fun decrease(quantity: Long): Stock {
        if (this.quantity - quantity < 0) {
            throw RuntimeException("재고가 0미만입니다.")
        }
        this.quantity -= quantity
        return this
    }
}
