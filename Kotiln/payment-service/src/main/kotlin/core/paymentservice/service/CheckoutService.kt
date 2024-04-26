package core.paymentservice.service

import core.paymentservice.controller.payment.response.CheckoutResponse
import core.paymentservice.dto.CheckoutCreationDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CheckoutService(
    private val loadProductService: LoadProductService,
    private val savePaymentService: SavePaymentService,
) {
    fun checkout(checkoutCreationDto: CheckoutCreationDto): Mono<CheckoutResponse> {
        return loadProductService.getProducts(checkoutCreationDto.cartId, checkoutCreationDto.productIds)
            .collectList()
            .map { products -> checkoutCreationDto.toPaymentEvent(products) }
            .flatMap { paymentEvent -> savePaymentService.save(paymentEvent) }
            .map { savedPaymentEvent ->
                CheckoutResponse(savedPaymentEvent)
            }
    }
}
