package chapter03.complexObject

data class Price private constructor(
    private val value: Double,
) {
    operator fun plus(price: Price) = Price(this.value + price.value)
    operator fun times(num: Int) = Price(this.value * num)

    companion object {
        val identity = Price(0.0)
        operator fun invoke(value: Double) =
            if (value > 0)
                Price(value)
            else
                throw IllegalArgumentException("Price must be positive")
    }
}

data class Weight private constructor(
    private val value: Double,
) {
    operator fun plus(weight: Weight) = Weight(this.value + weight.value)
    operator fun times(num: Int) = Weight(this.value * num)

    companion object {
        val identity = Weight(0.0)
        operator fun invoke(value: Double) =
            if (value > 0)
                Weight(value)
            else
                throw IllegalArgumentException("Weight must be positive")
    }
}

data class Product(
    val name: String,
    val price: Price,
    val weight: Weight,
)

data class OrderLine(
    val product: Product,
    val count: Int,
) {
    fun weight() = product.weight * count
    fun amount() = product.price * count
}

/**
 * 시작하는 원소를 제공하느냐 (fold), 제공하지 않느냐 (reduce)
 * 결과 타입이 컬렉션의 원소 타입과 동일하냐 (reduce), 동일하지 않느냐 (fold)
 * operator 함수들을 사용하여 collection 또는 sequence들을 편하게 처리할 수 있다.
 * 특히, invoke 는 경우에 따라 유용하게 사용될 수 있을 것 같다.
 */
object Store {
    @JvmStatic
    fun main(args: Array<String>) {
        val toothPaste = Product("Tooth Paste", Price(1.5), Weight(0.5))
        val toothBrush = Product("Tooth Brush", Price(3.5), Weight(0.3))
        val orderLines = listOf(
            OrderLine(toothPaste, 2),
            OrderLine(toothBrush, 3)
        )

        val weight: Weight = orderLines.fold(Weight.identity) { a, b -> a + b.weight() }
        val price: Price = orderLines.fold(Price.identity) { a, b -> a + b.amount() }

        println("Total Price: $price")
        println("Total Weight: $weight")
    }
}
