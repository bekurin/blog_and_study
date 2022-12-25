package chapter01.listing3

fun buyDonut(creditCard: CreditCard): Purchase =
    Purchase(Donut(), Payment(creditCard, Donut.price))
