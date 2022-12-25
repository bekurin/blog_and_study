package chapter01.listing5

data class Payment(
    val creditCard: CreditCard,
    val amount: Int,
) {
    fun combine(payment: Payment): Payment {
        return if (creditCard == payment.creditCard)
            Payment(creditCard, amount + payment.amount)
        else throw IllegalStateException("Cards don't match.")
    }

    companion object {
        fun groupByCard(payments: List<Payment>): List<Payment> {
            return payments.groupBy { it.creditCard }
                .values
                .map { it.reduce(Payment::combine) }
        }
    }
}