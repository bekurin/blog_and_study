package com.jpa.kotlinjpa.controller.dto

import com.jpa.kotlinjpa.entity.Teacher
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class TeacherResponseDto(
        val name: String
) {
    constructor(entity: Teacher) : this(
            name = entity.name
    )
}

data class HireDto(
        @field:NotBlank
        val name: String
) {
    fun toEntity(): Teacher {
        return Teacher(name)
    }
}
