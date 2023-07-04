package com.jpa.kotlinjpa.controller.dto

import com.jpa.kotlinjpa.entity.Teacher

data class TeacherResponseDto(
        val name: String
) {
    constructor(entity: Teacher) : this(
            name = entity.name
    )
}

data class HireDto(
        val name: String
) {
    fun toEntity(): Teacher {
        return Teacher(name)
    }
}
