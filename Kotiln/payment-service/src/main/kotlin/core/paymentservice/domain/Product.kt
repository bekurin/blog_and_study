package core.paymentservice.domain

import org.springframework.data.relational.core.mapping.Table

@Table("product")
class Product(
    amount: Long,
    quantity: Int,
    name: String,
    sellerId: Int
) : BaseEntity() {
    var amount: Long = amount
        private set

    var quantity: Int = quantity
        private set

    var name: String = name
        private set

    var sellerId: Int = sellerId
        private set
}
