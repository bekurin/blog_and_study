package com.jpa.kotlinjpa.controller.dto

import com.jpa.kotlinjpa.entity.Course
import com.jpa.kotlinjpa.entity.Teacher

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

data class RegisterDto(
        val teacherId: Long,
        val name: String,
        val capacity: Int
) {
    fun toEntity(teacher: Teacher): Course {
        return Course(teacher, name, capacity)
    }
}
