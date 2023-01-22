package com.example.logic.config

import com.example.logic.authentication.filter.InitialAuthenticationFilter
import com.example.logic.authentication.filter.JwtAuthenticationFilter
import com.example.logic.authentication.provider.OtpAuthenticationProvider
import com.example.logic.authentication.provider.UsernamePasswordAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class SecurityConfig(
    private val initialAuthenticationFilter: InitialAuthenticationFilter,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf().disable()
            .addFilterAt(
                initialAuthenticationFilter,
                BasicAuthenticationFilter::class.java
            )
            .addFilterAfter(
                jwtAuthenticationFilter,
                BasicAuthenticationFilter::class.java
            )
            .authorizeHttpRequests().anyRequest().authenticated()
            .and()
            .build()
    }
}

@Configuration
class Authentication(
    private val otpAuthenticationProvider: OtpAuthenticationProvider,
    private val usernamePasswordAuthenticationProvider: UsernamePasswordAuthenticationProvider,
) {
    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .authenticationProvider(otpAuthenticationProvider)
            .authenticationProvider(usernamePasswordAuthenticationProvider)
            .build()
    }
}