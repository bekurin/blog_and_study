package com.example.validation.controller.dto

import com.example.validation.exception.ValidatorException
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class SpringValidationRequest(
    @field:Min(1, message = "id는 0 미만일 수 없습니다")
    val id: Long,
    @field:NotBlank(message = "message는 빈칸일 수 없습니다")
    val message: String
) {
    init {
        if (message.length <= 10) {
            // 예외를 발생시키면 HttpMessageNotReadableException 이 발생한다.
            throw ValidatorException("메시지 사이즈는 10 이하여야합니다.")
        }
    }
}

data class ValidationRequest(
    val id: Long,
    val message: String
)
