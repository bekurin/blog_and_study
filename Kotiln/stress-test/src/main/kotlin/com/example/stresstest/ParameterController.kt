package com.example.stresstest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ParameterController {
    val log: Logger = LoggerFactory.getLogger(ParameterController::class.java)

    @PostMapping("/login-with-id-password")
    fun loginWithIdPassword(
        @RequestBody request: IdAndPassword
    ) {
        log.info("{} / {}", request.id, request.password)
    }

    data class IdAndPassword(
        val id: String,
        val password: String
    )
}
