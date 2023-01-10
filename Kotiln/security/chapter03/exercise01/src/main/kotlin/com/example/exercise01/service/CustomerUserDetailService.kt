package com.example.exercise01.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class CustomerUserDetailService(
    private val users: List<UserDetails>
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return users.asSequence()
            .filter { user -> user.username.equals(username) }
            .firstOrNull() ?: throw UsernameNotFoundException("User: $username not found")
    }
}