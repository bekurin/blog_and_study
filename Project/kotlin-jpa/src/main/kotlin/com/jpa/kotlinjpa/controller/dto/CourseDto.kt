package com.jpa.kotlinjpa.controller.dto

import com.jpa.kotlinjpa.entity.Course

data class CourseResponseDto(
        val teacher: String,
        val course: String,
        val capacity: Int
) {
    constructor(entity: Course) : this(
            course = entity.name,
            teacher = entity.teacher.name,
            capacity = entity.capacity
    )
}
