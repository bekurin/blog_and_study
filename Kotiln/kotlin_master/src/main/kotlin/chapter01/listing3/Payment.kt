package chapter01.listing3

class Payment(
    val creditCard: CreditCard,
    val amount: Int,
) {
    fun combine(payment: Payment): Payment {
        return if (creditCard == payment.creditCard)
            Payment(creditCard, amount + payment.amount)
        else throw IllegalStateException("Cards don't match")
    }
}

