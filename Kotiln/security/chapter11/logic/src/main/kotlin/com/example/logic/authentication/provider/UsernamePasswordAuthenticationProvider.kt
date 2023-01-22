package com.example.logic.authentication.provider

import UsernamePasswordAuthentication
import com.example.logic.authentication.proxy.AuthenticationServerProxy
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class UsernamePasswordAuthenticationProvider(
    private val proxy: AuthenticationServerProxy,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()
        proxy.sendAuth(username, password)
        return UsernamePasswordAuthenticationToken(username, password)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return UsernamePasswordAuthentication::class.java.isAssignableFrom(authentication)
    }
}