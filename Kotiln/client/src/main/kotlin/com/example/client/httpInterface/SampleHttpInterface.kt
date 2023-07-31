package com.example.client.httpInterface

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.core.publisher.Mono

interface SampleHttpInterface {
    @GetExchange(url = "/", accept = [MediaType.APPLICATION_JSON_VALUE])
    fun getGoogleHtml(): Mono<String>
}

@Configuration
class SampleHttpInterfaceConfig {
    @Bean
    fun sampleHttpInterface(): SampleHttpInterface {
        val webClient = WebClient.builder()
                .baseUrl("https://google.com")
                .build()
        val httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(WebClientAdapter.forClient(webClient)).build()
        return httpServiceProxyFactory.createClient(SampleHttpInterface::class.java)
    }
}
