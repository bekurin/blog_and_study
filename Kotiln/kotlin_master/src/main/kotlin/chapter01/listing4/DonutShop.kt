package chapter01.listing4

fun buyDonuts(
    quantity: Int = 1,
    creditCard: CreditCard,
): Purchase {
    return Purchase(List(quantity) { Donut() }, Payment(creditCard, Donut.price * quantity))
}
