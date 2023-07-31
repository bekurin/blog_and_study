package com.example.client.webClient

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class SampleWebClient(
        private val webClient: WebClient
) {
    fun getGoogleHtml(): Mono<String> {
        return webClient
                .get()
                .uri("/")
                .retrieve()
                .bodyToMono(String::class.java)
    }
}

@Configuration
class SampleWebClientConfig {
    @Bean
    fun webClient(): WebClient {
        return WebClient
                .builder()
                .baseUrl("https://google.com")
                .build()
    }
}
