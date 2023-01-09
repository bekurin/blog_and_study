package com.example.exercise05.config

import com.example.exercise05.security.CustomAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class ProjectConfig(
    val authenticationProvider: CustomAuthenticationProvider,
) {
    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .authenticationProvider(authenticationProvider)
            .build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.httpBasic()
            .and().authorizeHttpRequests().anyRequest().authenticated()
            .and().build()
    }
}