package com.example.client.restTemplate

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SampleRestTemplate {
    fun getGoogleHtml(): ResponseEntity<String> {
        return RestTemplate()
                .getForEntity("https://google.com", String::class.java)
    }
}
