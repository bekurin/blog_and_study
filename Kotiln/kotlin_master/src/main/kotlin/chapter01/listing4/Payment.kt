package chapter01.listing4

class Payment(
    val creditCard: CreditCard,
    val amount: Int,
) {
    fun combine(payment: Payment): Payment {
        if (creditCard == payment.creditCard) {
            return Payment(creditCard, payment.amount + amount)
        } else throw IllegalStateException("Cards don't match")
    }
}