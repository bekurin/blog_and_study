package com.example.exercise01.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello(auth: Authentication): String {
        return """
            ${auth.name} ||
            ${auth.credentials} ||
            ${auth.authorities} ||
            ${auth.isAuthenticated} ||
            ${auth.principal} ||
            ${auth.details}
        """.trimIndent()
    }
}