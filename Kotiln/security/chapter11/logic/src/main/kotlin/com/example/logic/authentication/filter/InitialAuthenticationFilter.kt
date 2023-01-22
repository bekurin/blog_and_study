package com.example.logic.authentication.filter

import UsernamePasswordAuthentication
import com.example.logic.authentication.OtpAuthentication
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets

@Component
class InitialAuthenticationFilter(
    private val authenticationManager: AuthenticationManager
) : OncePerRequestFilter() {

    @Value("\${jwt.signing.key}")
    private lateinit var signingKey: String

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val username = request.getHeader("username")
        val password = request.getHeader("password")
        val code = request.getHeader("code")

        if (code == null) {
            val auth = UsernamePasswordAuthentication(username, password)
            authenticationManager.authenticate(auth)
        } else {
            val auth = OtpAuthentication(username, code)
            authenticationManager.authenticate(auth)
            val key = Keys.hmacShaKeyFor(signingKey.toByteArray(StandardCharsets.UTF_8))
            val jwt = Jwts.builder()
                .setClaims(mapOf("username" to username))
                .signWith(key)
                .compact()
            response.setHeader("Authorization", jwt)
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath != "/login"
    }
}