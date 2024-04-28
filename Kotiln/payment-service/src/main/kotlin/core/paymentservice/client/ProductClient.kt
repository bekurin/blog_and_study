package core.paymentservice.client

import core.paymentservice.domain.Product
import reactor.core.publisher.Flux

interface ProductClient {
    fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product>
}
