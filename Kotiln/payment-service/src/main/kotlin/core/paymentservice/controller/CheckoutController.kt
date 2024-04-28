package core.paymentservice.controller

import core.paymentservice.controller.dto.CheckoutCreationRequest
import core.paymentservice.service.CheckoutService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@Controller
class CheckoutController(
    private val checkoutService: CheckoutService
) {
    @GetMapping("/")
    fun checkout(request: CheckoutCreationRequest, model: Model): Mono<String> {
        return checkoutService.checkout(request.toCheckoutCommand())
            .map {
                model.addAttribute("orderId", it.orderId)
                model.addAttribute("orderName", it.orderName)
                model.addAttribute("amount", it.amount)
                "checkout"
            }
    }
}
