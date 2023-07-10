package com.jpa.kotlinjpa.controller.dto

import com.jpa.kotlinjpa.entity.Student
import jakarta.validation.constraints.NotBlank

data class StudentSignInDto(
        @field:NotBlank
        val name: String,
        @field:NotBlank
        val email: String,
        @field:NotBlank
        val phone: String
) {
    fun toEntity(): Student {
        return Student(name, email, phone)
    }
}

data class StudentResponseDto(
        val name: String,
        val email: String,
        val phone: String,
        val enrolls: List<EnrollResponseDto>
) {
    constructor(entity: Student) : this(
            name = entity.name,
            email = entity.email,
            phone = entity.phone,
            enrolls = entity.enrolls.map { EnrollResponseDto(it) }
    )
}
