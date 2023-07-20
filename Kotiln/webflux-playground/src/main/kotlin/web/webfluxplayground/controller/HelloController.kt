package web.webfluxplayground.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HelloController {

    @GetMapping("/api/v1")
    fun hello(): Mono<String> {
        return Mono.just("hello")
    }

    @GetMapping("/api/v1/admin")
    fun adminHello(): Mono<String> {
        return Mono.just("admin hello!")
    }
}
