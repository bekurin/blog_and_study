package com.example.exercise06.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class WebAuthorizationConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.httpBasic()
            .and().authorizeHttpRequests().anyRequest().authenticated()
            .and().build()
    }
}