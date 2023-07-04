package com.jpa.kotlinjpa.sevice

import com.jpa.kotlinjpa.controller.dto.RegisterDto
import com.jpa.kotlinjpa.entity.Course
import com.jpa.kotlinjpa.exception.ResourceNotFoundException
import com.jpa.kotlinjpa.repository.CourseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CourseService(
        private val teacherService: TeacherService,
        private val courseRepository: CourseRepository
) {
    @Transactional
    fun register(dto: RegisterDto): Course {
        val teacher = teacherService.findByIdOrThrow(dto.teacherId)
        return courseRepository.save(dto.toEntity(teacher))
    }

    fun findByIdOrThrow(id: Long): Course {
        return courseRepository.findById(id)
                .orElseThrow { throw ResourceNotFoundException("수업을 찾을 수 없습니다. (id=$id)") }
    }
}
