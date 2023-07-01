package com.jpa.kotlinjpa.controller.dto

import com.jpa.kotlinjpa.entity.Enroll

data class EnrollResponseDto(
        val course: CourseResponseDto,
) {
    constructor(entity: Enroll) : this(
            course = CourseResponseDto(entity.course)
    )
}
