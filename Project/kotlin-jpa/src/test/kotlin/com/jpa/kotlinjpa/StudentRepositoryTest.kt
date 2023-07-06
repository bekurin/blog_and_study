package com.jpa.kotlinjpa

import com.jpa.kotlinjpa.entity.Student
import com.jpa.kotlinjpa.repository.StudentRepository
import com.jpa.kotlinjpa.util.Fixture.StudentFixture
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport

@DataJpaTest(showSql = true)
class StudentRepositoryTest {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var studentRepository: StudentRepository

    private val entityInformation: JpaEntityInformation<Student, *> by lazy {
        JpaEntityInformationSupport
                .getEntityInformation(Student::class.java, entityManager)
    }

    @Test
    fun `저장 후 영속 상태 확인`() {
        val student = StudentFixture.anyStudent()
        println("새로운 객체인가? = ${entityInformation.isNew(student)}")
        println("현재 ID는 무엇인가? = ${student.getId()}")
        studentRepository.save(student)

        val findStudent = studentRepository.findById(student.getId()).get()
        println("새로운 객체인가? = ${entityInformation.isNew(student)}")
        println("현재 ID는 무엇인가? = ${findStudent.getId()}")
        assertThat(student).isEqualTo(findStudent)
    }

    @Test
    fun `save 호출 후 ID로 찾으면`() {
        val student = StudentFixture.anyStudent()
        studentRepository.save(student)

        val findStudent = studentRepository.findById(student.getId()).get()
        println("새로운 객체인가? = ${entityInformation.isNew(findStudent)}")
        println("현재 ID는 무엇인가? = ${findStudent.getId()}")
    }

    @Test
    fun `더티 체킹이 정상동작하는지 확인`() {
        val student = StudentFixture.anyStudent()
        println("현재 영속 상태인가? = ${entityInformation.isNew(student)}")
        println("현재 ID는 무엇인가? = ${student.getId()}")
        studentRepository.save(student)
        entityManager.flush()

        val findStudent = studentRepository.findById(student.getId()).get()
        println("현재 영속 상태인가? = ${entityInformation.isNew(findStudent)}")
        println("현재 ID는 무엇인가? = ${findStudent.getId()}")
        findStudent.update("test123@gmail.com")
        entityManager.flush()
        assertThat(findStudent.email).isEqualTo(studentRepository.findById(findStudent.getId()).get().email)
    }

    @Test
    fun `2개 이상 Update 쿼리 확인`() {
        val students = (1..10).map { StudentFixture.of() }
        studentRepository.saveAllAndFlush(students)

        val findStudents = studentRepository.findAll()
        findStudents.map { it.update("test123123123@gmail.com") }
        entityManager.flush()
    }
}
