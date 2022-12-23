package chapter01.listing2

fun buyDonut(creditCard: CreditCard): Purchase =
    Purchase(Donut(), Payment(creditCard, Donut.price))