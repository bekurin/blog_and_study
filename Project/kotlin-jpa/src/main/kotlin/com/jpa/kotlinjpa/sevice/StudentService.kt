package com.jpa.kotlinjpa.sevice

import com.jpa.kotlinjpa.controller.dto.StudentResponseDto
import com.jpa.kotlinjpa.controller.dto.StudentSignInDto
import com.jpa.kotlinjpa.exception.ClientBadRequestException
import com.jpa.kotlinjpa.repository.StudentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StudentService(
        private val studentRepository: StudentRepository
) {

//    @Transactional
    fun signIn(dto: StudentSignInDto): StudentResponseDto {
        return StudentResponseDto(studentRepository.save(dto.toEntity()))
    }

    fun findMemberById(id: Long): StudentResponseDto {
        val findStudent = studentRepository.findById(id)
                .orElseThrow { throw ClientBadRequestException("회원을 찾을 수 없습니다. (id=$id)") }
        return StudentResponseDto(findStudent)
    }

    fun findAll(): List<StudentResponseDto> {
        return studentRepository.findAll()
                .map { StudentResponseDto(it) }
    }
}
