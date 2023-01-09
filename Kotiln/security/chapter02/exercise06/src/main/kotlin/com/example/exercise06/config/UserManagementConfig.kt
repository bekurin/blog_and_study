package com.example.exercise06.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
class UserManagementConfig {
    @Bean
    fun userDetailsService(): UserDetailsService {
        val userDetailsService = InMemoryUserDetailsManager()
        val encoder = passwordEncoder()

        val user = User.withUsername("Bob")
            .password(encoder.encode("12345"))
            .authorities("read")
            .build()
        userDetailsService.createUser(user)
        return userDetailsService
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}