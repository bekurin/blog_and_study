package core.paymentservice.controller.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@Controller
class CheckoutController {
    @GetMapping("/")
    fun checkout(): Mono<String> {
        return Mono.just("checkout")
    }
}
