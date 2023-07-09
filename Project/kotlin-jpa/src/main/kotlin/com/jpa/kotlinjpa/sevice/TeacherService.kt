package com.jpa.kotlinjpa.sevice

import com.jpa.kotlinjpa.controller.dto.HireDto
import com.jpa.kotlinjpa.controller.dto.TeacherResponseDto
import com.jpa.kotlinjpa.exception.ResourceNotFoundException
import com.jpa.kotlinjpa.repository.TeacherRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TeacherService(
        private val teacherRepository: TeacherRepository
) {
    @Transactional
    fun hire(dto: HireDto): TeacherResponseDto {
        return TeacherResponseDto(teacherRepository.save(dto.toEntity()))
    }

    fun findByIdOrThrow(id: Long): TeacherResponseDto {
        val findTeacher = teacherRepository.findById(id)
                .orElseThrow { throw ResourceNotFoundException("선생님 정보가 없습니다. (id=$id)") }
        return TeacherResponseDto(findTeacher)
    }
}
