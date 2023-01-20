package com.example.exercise02.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class ProjectConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.httpBasic { c ->
            c.realmName("OTHER");
            c.authenticationEntryPoint(CustomEntryPoint())
        }
        return http
            .authorizeHttpRequests().anyRequest().authenticated()
            .and()
            .build()
    }
}