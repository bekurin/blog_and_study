package chapter05

class Listing9 {
    class CustomerController(
        val emailGateway: EmailGateway,
    ) {
        private lateinit var customerRepository: CustomerRepository
        private lateinit var productRepository: ProductRepository
        private lateinit var mainStore: StoreImpl

        fun purchase(customerId: Int, productId: Int, quantity: Int): Boolean {
            val customer = customerRepository.getById(customerId)
            val product = productRepository.getById(productId)

            val success = customer.purchase(mainStore, product, quantity)
            if (success) {
                emailGateway.sendReceipt(customer.email, product.name, quantity)
            }
            return success
        }
    }

    class EmailGatewayImpl : EmailGateway {
        override fun sendReceipt(email: String, productName: String, quantity: Int) {
            TODO("Not yet implemented")
        }

    }

    interface EmailGateway {
        fun sendReceipt(email: String, productName: String, quantity: Int)
    }

    internal class ProductRepository {
        fun getById(productId: Int): Product {
            return Product()
        }
    }

    internal class CustomerRepository {
        fun getById(customerId: Int): Customer {
            return Customer()
        }
    }

    interface Store {
        fun hasEnoughInventory(product: Product, quantity: Int): Boolean
        fun removeInventory(product: Product, quantity: Int)
        fun addInventory(product: Product, quantity: Int)
        fun getInventory(product: Product): Int
    }

    class StoreImpl : Store {
        var id: Int = 0
        private val inventory = hashMapOf<Product, Int>()

        override fun hasEnoughInventory(product: Product, quantity: Int): Boolean {
            return getInventory(product) >= quantity
        }

        override fun removeInventory(product: Product, quantity: Int) {
            if (!hasEnoughInventory(product, quantity)) {
                throw Exception("not enough inventory")
            }
            inventory[product] = inventory[product]!! - (quantity)
        }

        override fun addInventory(product: Product, quantity: Int) {
            if (inventory.containsKey(product)) {
                inventory[product] = inventory[product]!! + quantity
            } else {
                inventory[product] = quantity
            }
        }

        override fun getInventory(product: Product): Int {
            return inventory[product] ?: 0
        }

    }

    data class Product(
        val id: Int,
        val name: String,
    ) {
        constructor(name: String) : this(0, name)
        constructor() : this(0, "")
    }

    class Customer(
        val email: String,
    ) {
        constructor() : this("")

        fun purchase(store: Store, product: Product, quantity: Int): Boolean {
            if (!store.hasEnoughInventory(product, quantity)) {
                return false
            }
            store.removeInventory(product, quantity)
            return true
        }
    }
}