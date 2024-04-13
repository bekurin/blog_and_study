package core.jdbcclient.service

import core.jdbcclient.domain.Student
import core.jdbcclient.repository.StudentRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class StudentServiceTest {

    @Autowired
    private lateinit var sut: StudentService

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Test
    fun `jdbcClient로 선언된 findByPhone 함수를 사용할 수 있다`() {
        // given
        val givenPhone = "010-1234-1234"
        val expectedStudent = Student("홍길동", givenPhone)
        studentRepository.save(expectedStudent)

        // when
        val foundStudent = sut.findByPhone(givenPhone)

        // then
        assert(foundStudent.id == expectedStudent.id)
        assert(foundStudent.name == expectedStudent.name)
        assert(foundStudent.phone == expectedStudent.phone)
    }

}
