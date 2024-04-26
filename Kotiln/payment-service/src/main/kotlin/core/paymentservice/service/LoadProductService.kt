package core.paymentservice.service

import core.paymentservice.client.ProductClient
import core.paymentservice.domain.Product
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class LoadProductService(
    private val productClient: ProductClient
) {
    fun getProducts(cartId: Int, productIds: List<Int>): Flux<Product> {
        return productClient.getProducts(cartId, productIds)
    }
}
