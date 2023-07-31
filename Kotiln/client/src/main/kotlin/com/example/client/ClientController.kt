package com.example.client

import com.example.client.httpInterface.SampleHttpInterface
import com.example.client.restClient.SampleRestClient
import com.example.client.restTemplate.SampleRestTemplate
import com.example.client.webClient.SampleWebClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ClientController(
        private val sampleHttpInterface: SampleHttpInterface,
        private val sampleRestClient: SampleRestClient,
        private val sampleWebClient: SampleWebClient,
        private val sampleRestTemplate: SampleRestTemplate

) {
    @GetMapping("/http-interface")
    fun httpInterface(): Mono<String> {
        return sampleHttpInterface.getGoogleHtml()
    }

    @GetMapping("/rest-client")
    fun restClient(): String? {
        return sampleRestClient.getGoogleHtml()
    }

    @GetMapping("/web-client")
    fun webClient(): Mono<String> {
        return sampleWebClient.getGoogleHtml()
    }

    @GetMapping("/rest-template")
    fun restTemplate(): ResponseEntity<String> {
        return sampleRestTemplate.getGoogleHtml()
    }
}
