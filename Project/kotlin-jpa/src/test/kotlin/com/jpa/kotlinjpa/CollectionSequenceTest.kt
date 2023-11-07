package com.jpa.kotlinjpa

import com.jpa.kotlinjpa.entity.Course
import com.jpa.kotlinjpa.entity.Enroll
import com.jpa.kotlinjpa.entity.Student
import com.jpa.kotlinjpa.entity.Teacher
import com.jpa.kotlinjpa.repository.CourseRepository
import com.jpa.kotlinjpa.repository.EnrollRepository
import com.jpa.kotlinjpa.repository.StudentRepository
import com.jpa.kotlinjpa.repository.TeacherRepository
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Rollback

@DataJpaTest
class CollectionSequenceTest {

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var enrollRepository: EnrollRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    var givenStudentId: Long = 0

    @BeforeEach
    fun setUp() {
        val student = Student("아무개", "test@gmail.com", "000-0000-0004")
        studentRepository.save(student)
        (1..10).map {
            val teacher = Teacher("선생$it")
            teacherRepository.save(teacher)
            val course = Course(teacher, "수영 ${it}반", 10)
            courseRepository.save(course)
            val enroll = Enroll(course, student)
            val savedEnroll = enrollRepository.save(enroll)
            savedEnroll.updateStudent(student)
        }
        givenStudentId = student.getId()
        entityManager.flush()
    }

    @Test
    @Rollback(false)
    fun `collection vs sequence`() {
        // when
        val findStudent = studentRepository.findById(givenStudentId).get()

        // then
        println(findStudent.enrolls)
        entityManager.flush()
    }
}
