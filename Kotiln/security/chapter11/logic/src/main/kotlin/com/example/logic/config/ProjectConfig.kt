package com.example.logic.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ProjectConfig {

    /**
     * 동기 방식으로 요청을 처리하고, 추후 deprecated 될 기능이다.
     */
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}