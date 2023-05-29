package com.example.validation.controller.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class ValidationTestRequest(
    @field:Min(1, message = "id는 0 미만일 수 없습니다")
    val id: Long,
    @field:NotBlank(message = "message는 빈칸일 수 없습니다")
    val message: String
)
