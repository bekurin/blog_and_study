package core.r2dbch2.utils

import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

inline fun <reified T> WebClient.doGetAsMono(uri: String): Mono<T> {
    return this.get()
        .uri(uri)
        .retrieve()
        .bodyToMono(T::class.java)
}