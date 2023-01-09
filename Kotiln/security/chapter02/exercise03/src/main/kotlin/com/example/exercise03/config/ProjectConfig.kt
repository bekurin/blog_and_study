package com.example.exercise03.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class ProjectConfig {
    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val userDetailsService = InMemoryUserDetailsManager()
        val encoder = BCryptPasswordEncoder()

        val user = User
            .withUsername("Bob")
            .password(encoder.encode("12345"))
            .authorities("read")
            .build()
        userDetailsService.createUser(user)

        return http
            .getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userDetailsService)
            .passwordEncoder(encoder)
            .and()
            .build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.httpBasic()
            .and().authorizeHttpRequests().anyRequest().authenticated()
            .and().build()
    }
}