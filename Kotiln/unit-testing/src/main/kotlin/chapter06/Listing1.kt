package chapter06

import kotlin.math.min

class Listing1 {
    class PriceEngine {
        fun calculateDiscount(product: List<Product>): Double {
            val discount = product.size * 0.01
            return min(discount, 0.2)
        }
    }

    class Product(
        val name: String,
    )
}