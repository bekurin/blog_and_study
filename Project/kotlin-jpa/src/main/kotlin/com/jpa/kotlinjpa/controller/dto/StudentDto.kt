package com.jpa.kotlinjpa.controller.dto

import com.jpa.kotlinjpa.entity.Student

data class StudentSignInDto(
        val name: String,
        val email: String,
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
