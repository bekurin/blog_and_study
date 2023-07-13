package com.jpa.kotlinjpa.facade

import com.jpa.kotlinjpa.controller.dto.EnrollDto
import com.jpa.kotlinjpa.entity.Enroll
import com.jpa.kotlinjpa.sevice.CourseService
import com.jpa.kotlinjpa.sevice.EnrollService
import com.jpa.kotlinjpa.sevice.StudentService
import org.springframework.stereotype.Service

@Service
class EnrollFacade(
        private val enrollService: EnrollService,
        private val courseService: CourseService,
        private val studentService: StudentService
) {
    fun enroll(dto: EnrollDto): Enroll {
        val findCourse = courseService.findByIdOrThrow(dto.courseId)
        val findStudent = studentService.findByIdOrThrow(dto.studentId)
        return enrollService.enroll(dto.toEntity(findCourse, findStudent))
    }
}
