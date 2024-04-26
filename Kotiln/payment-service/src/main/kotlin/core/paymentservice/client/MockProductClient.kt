package core.paymentservice.client

import core.paymentservice.domain.Product
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class MockProductClient() : ProductClient {
    override fun getProducts(cartId: Int, productIds: List<Int>): Flux<Product> {
        return Flux.fromIterable(
            productIds.map { productId ->
                Product(
                    amount = productId * 10_000L,
                    quantity = productId,
                    name = "상품#$productId",
                    sellerId = 1
                )
                    .also { it.id = productId }
            }
        )
    }
}
