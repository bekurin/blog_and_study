package com.example.logic.authentication.provider

import com.example.logic.authentication.OtpAuthentication
import com.example.logic.authentication.proxy.AuthenticationServerProxy
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class OtpAuthenticationProvider(
    private val proxy: AuthenticationServerProxy,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val code = authentication.credentials.toString()
        val result = proxy.sendOTP(username, code)

        if (result) {
            return OtpAuthentication(username, code)
        } else {
            throw BadCredentialsException("Bad Credentials!")
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return OtpAuthentication::class.java.isAssignableFrom(authentication)
    }

}