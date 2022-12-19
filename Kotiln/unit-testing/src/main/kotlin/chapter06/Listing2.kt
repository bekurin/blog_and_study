package chapter06

class Listing2 {
    class Order(
        val products: MutableList<Product> = mutableListOf(),
    ) {
        fun addProduct(product: Product) {
            products.add(product)
        }
    }

    class Product(
        val name: String,
    )
}