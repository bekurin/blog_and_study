package com.example.onetimepassword

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OneTimePasswordController(
    private val oneTimePasswordService: OneTimePasswordService
) {

    @PostMapping("/one-time-password")
    fun createOneTimePassword(
        @RequestBody request: CreateOneTimePasswordRequest
    ): String {
        return oneTimePasswordService.create(request.phone)
    }

    @PostMapping("/one-time-password/verify")
    fun verifyOneTimePassword(
        @RequestBody request: VerifyOneTimePasswordRequest
    ): Boolean {
        return oneTimePasswordService.verify(request.phone, request.oneTimePassword)
    }

    data class VerifyOneTimePasswordRequest(
        val phone: String,
        val oneTimePassword: String
    )

    data class CreateOneTimePasswordRequest(
        val phone: String
    )

}
