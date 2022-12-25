package chapter01.listing5

fun buyDonuts(quantity: Int = 1, creditCard: CreditCard): Purchase {
    return Purchase(List(quantity) { Donut() }, Payment(creditCard, Donut.price * quantity))
}
