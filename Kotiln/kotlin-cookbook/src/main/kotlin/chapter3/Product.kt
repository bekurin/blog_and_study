package chapter3

data class Product(
    val name: String,
    val price: Double,
    var onSale: Boolean = false
)

data class OrderItem(
    val product: Product,
    val quantity: Int
)
