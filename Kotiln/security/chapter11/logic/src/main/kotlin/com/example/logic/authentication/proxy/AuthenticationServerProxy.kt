package com.example.logic.authentication.proxy

import com.example.logic.authentication.model.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class AuthenticationServerProxy(
    private val restTemplate: RestTemplate,
) {
    @Value("\${auth.server.base.url}")
    private lateinit var baseUrl: String

    fun sendAuth(username: String, password: String) {
        val url = "${baseUrl}/user/auth"
        val body = User(username, password)
        restTemplate.postForEntity(url, HttpEntity(body), Void::class.java)
    }

    fun sendOTP(username: String, code: String): Boolean {
        val url = "${baseUrl}/otp/check"
        val body = User(username, code = code)
        val response = restTemplate.postForEntity(url, HttpEntity(body), Void::class.java)
        return response.statusCode == HttpStatus.OK
    }
}