package com.example.exercise01.config

import com.example.exercise01.model.User
import com.example.exercise01.service.CustomerUserDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ProjectConfig {

    @Bean
    fun userDetailsService(): UserDetailsService {
        val encoder = passwordEncoder()
        val users = listOf<User>(
            User("john", encoder.encode("12345"), "read"),
            User("Bob", encoder.encode("12345"), "read")
        )
        return CustomerUserDetailService(users)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}