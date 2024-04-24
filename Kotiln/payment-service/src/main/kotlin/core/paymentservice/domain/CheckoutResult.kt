package core.paymentservice.domain

data class CheckoutResult(
    val amount: Long,
    val orderKey: String,
    val orderName: String,
) : BaseEntity()
