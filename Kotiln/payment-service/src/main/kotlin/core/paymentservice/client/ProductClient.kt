package core.paymentservice.client

import core.paymentservice.domain.Product
import reactor.core.publisher.Flux

interface ProductClient {
    fun getProducts(cartId: Int, productIds: List<Int>): Flux<Product>
}
