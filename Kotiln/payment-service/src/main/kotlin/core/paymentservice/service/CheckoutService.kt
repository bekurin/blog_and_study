package core.paymentservice.service

import core.paymentservice.controller.dto.CheckoutResponse
import core.paymentservice.service.dto.CheckoutCommand
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CheckoutService(
    private val loadProductService: LoadProductService,
    private val savePaymentService: SavePaymentService,
) {
    fun checkout(checkoutCommand: CheckoutCommand): Mono<CheckoutResponse> {
        return loadProductService.getProducts(checkoutCommand.cartId, checkoutCommand.productIds)
            .collectList()
            .map { products -> checkoutCommand.toPaymentEvent(products) }
            .flatMap { paymentEvent -> savePaymentService.save(paymentEvent) }
            .map { savedPaymentEvent -> CheckoutResponse(savedPaymentEvent) }
    }
}
