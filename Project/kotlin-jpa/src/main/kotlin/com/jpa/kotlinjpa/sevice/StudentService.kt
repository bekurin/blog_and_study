package com.jpa.kotlinjpa.sevice

import com.jpa.kotlinjpa.controller.dto.StudentResponseDto
import com.jpa.kotlinjpa.controller.dto.StudentSignInDto
import com.jpa.kotlinjpa.entity.Student
import com.jpa.kotlinjpa.exception.ClientBadRequestException
import com.jpa.kotlinjpa.repository.StudentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StudentService(
        private val studentRepository: StudentRepository
) {

    @Transactional
    fun signIn(dto: StudentSignInDto): Student {
        return studentRepository.save(dto.toEntity())
    }

    fun findMemberById(id: Long): Student {
        val findStudent = studentRepository.findById(id)
                .orElseThrow { throw ClientBadRequestException("회원을 찾을 수 없습니다. (id=$id)") }
        findStudent.update("test@gmail.comasdasd")
        return findStudent
    }

    fun findAll(): List<Student> {
        return studentRepository.findAll()
    }
}
