package chapter2

class Listing2 {
    interface Store {
        fun hasEnoughInventory(product: Product, quantity: Int): Boolean
        fun removeInventory(product: Product, quantity: Int): Unit
        fun addInventory(product: Product, quantity: Int): Unit
        fun getInventory(product: Product): Int
    }

    class StoreImpl : Store {
        private val inventory = mutableMapOf<Product, Int>()


        override fun hasEnoughInventory(product: Product, quantity: Int): Boolean {
            return getInventory(product) >= quantity
        }

        override fun removeInventory(product: Product, quantity: Int) {
            if (!hasEnoughInventory(product, quantity)) {
                throw Exception("Not enough inventory")
            }
            inventory[product] = inventory[product]!!.minus(quantity)
        }

        override fun addInventory(product: Product, quantity: Int) {
            if (inventory.contains(product)) {
                inventory[product] = inventory[product]!!.plus(quantity)
            } else {
                inventory[product] = quantity
            }
        }

        override fun getInventory(product: Product): Int {
            return inventory[product] ?: 0
        }
    }

    enum class Product {
        SHAMPOO, BOOK
    }

    class Customer {
        fun purchase(store: Store, product: Product, quantity: Int): Boolean {
            if (!store.hasEnoughInventory(product, quantity)) {
                return false
            }
            store.removeInventory(product, quantity)
            return true
        }
    }
}