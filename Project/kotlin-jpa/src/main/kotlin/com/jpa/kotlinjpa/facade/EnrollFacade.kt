package com.jpa.kotlinjpa.facade

import com.jpa.kotlinjpa.controller.dto.EnrollDto
import com.jpa.kotlinjpa.entity.Enroll
import com.jpa.kotlinjpa.exception.ClientBadRequestException
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
        val findStudent = studentService.findByIdOrThrow(dto.studentId)
        val enrolledIds = findStudent.enrolls.map { it.course.getId() }
        if (enrolledIds.contains(dto.courseId)) {
            throw ClientBadRequestException("이미 강의에 등록되어 있습니다. (studentId=${dto.studentId}, courseId=${dto.courseId})")
        }
        val findCourse = courseService.findByIdOrThrow(dto.courseId)
        println()
        return enrollService.enroll(dto.toEntity(findCourse, findStudent))
    }
}
