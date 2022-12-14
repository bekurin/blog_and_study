package chapter2

class Listing1 {
    class Store {
        private var inventory: MutableMap<Product, Int> = mutableMapOf()

        fun hasEnoughInventory(product: Product, quantity: Int): Boolean {
            return getInventory(product) >= quantity
        }

        fun removeInventory(product: Product, quantity: Int) {
            if (!hasEnoughInventory(product, quantity)) {
                throw Exception("Not enough inventory")
            }
            inventory[product] = inventory[product]!!.minus(quantity)
        }

        fun addInventory(product: Product, quantity: Int) {
            if (inventory.contains(product)) {
                inventory[product] = inventory[product]!!.plus(quantity)
            } else {
                inventory[product] = quantity
            }
        }

        fun getInventory(product: Product): Int {
            return inventory[product] ?: 0
        }
    }

    enum class Product {
        SHAMPOO,
        BOOK
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