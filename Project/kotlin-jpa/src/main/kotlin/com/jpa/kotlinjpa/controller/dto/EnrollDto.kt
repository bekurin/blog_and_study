package com.jpa.kotlinjpa.controller.dto

import com.jpa.kotlinjpa.entity.Course
import com.jpa.kotlinjpa.entity.Enroll
import com.jpa.kotlinjpa.entity.Student

data class EnrollResponseDto(
        val course: CourseResponseDto,
        val student: StudentResponseDto
) {
    constructor(entity: Enroll) : this(
            course = CourseResponseDto(entity.course),
            student = StudentResponseDto(entity.student)
    )
}

data class EnrollDto(
        val courseId: Long,
        val studentId: Long
) {

    fun toEntity(course: Course, student: Student): Enroll {
        return Enroll(course, student)
    }
}
