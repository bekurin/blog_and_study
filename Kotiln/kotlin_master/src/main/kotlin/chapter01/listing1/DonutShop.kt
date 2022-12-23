package chapter01.listing1

fun buyDonut(creditCard: CreditCard): Donut {
    val donut = Donut()
    creditCard.charge(Donut.price)
    return donut
}