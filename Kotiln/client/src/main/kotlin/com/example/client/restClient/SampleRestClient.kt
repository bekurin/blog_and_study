package com.example.client.restClient

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class SampleRestClient(
        private val restClient: RestClient
) {
    fun getGoogleHtml(): String? {
        return restClient
                .get()
                .uri("/")
                .retrieve()
                .body(String::class.java)
    }
}

@Configuration
class SampleRestClientConfig {
    @Bean
    fun restClient(): RestClient {
        return RestClient
                .builder()
                .baseUrl("https://google.com")
                .build()
    }
}

