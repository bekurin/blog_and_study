package core.jdbcclient.service

import core.jdbcclient.domain.Student
import core.jdbcclient.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {
    fun findByPhone(phone: String): Student {
        return studentRepository.findByPhone(phone)
            .orElseThrow { RuntimeException("회원을 찾을 수 없습니다.") }
    }
}
