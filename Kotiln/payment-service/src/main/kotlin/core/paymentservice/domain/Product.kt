package core.paymentservice.domain

import org.springframework.data.relational.core.mapping.Table

@Table("product")
data class Product(
    val amount: String,
    val quantity: Int,
    val name: String,
    val sellerId: Long
) : BaseEntity()
