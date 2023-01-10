package com.example.exercise01.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.List

class User(
    private val username: String,
    private val password: String,
    private val authority: String,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return listOf(GrantedAuthority { authority })
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}