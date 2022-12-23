package chapter01.listing1

class CreditCard {
    var total: Int = 0
        private set

    fun charge(price: Int) {
        this.total += price
    }
}