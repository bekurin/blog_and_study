package com.jpa.kotlinjpa

import com.jpa.kotlinjpa.entity.Course
import com.jpa.kotlinjpa.entity.Enroll
import com.jpa.kotlinjpa.entity.Student
import com.jpa.kotlinjpa.entity.Teacher
import com.jpa.kotlinjpa.repository.CourseRepository
import com.jpa.kotlinjpa.repository.EnrollRepository
import com.jpa.kotlinjpa.repository.StudentRepository
import com.jpa.kotlinjpa.repository.TeacherRepository
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@SpringBootApplication
@EnableJpaAuditing
class KotlinJpaApplication

fun main(args: Array<String>) {
    runApplication<KotlinJpaApplication>(*args)
}

@Component
@Transactional
class Initializer(
        private val studentRepository: StudentRepository,
        private val teacherRepository: TeacherRepository,
        private val enrollRepository: EnrollRepository,
        private val courseRepository: CourseRepository,
) {

    @PostConstruct
    fun init() {
        val teacher = Teacher("김팔각")
        val course1 = Course(teacher, "수영 고급반", 10)
        val course2 = Course(teacher, "수영 중급반", 10)
        courseRepository.saveAll(listOf(course1, course2))

        val student1 = Student("홍길동1", "test1@gmail.com", "01012409522")
        val student2 = Student("홍길동2", "test2@gmail.com", "01011232324")
        val student3 = Student("홍길동3", "test3@gmail.com", "01011234123")
        val enroll1 = Enroll(course1, student1)
        val enroll2 = Enroll(course1, student2)
        val enroll3 = Enroll(course2, student3)

        val studentList = (5..10000000L).map {
            Student("홍길동$it", "test$it@gmail.com", "010112341$it")
        }
        studentRepository.saveAll(studentList)
        enrollRepository.saveAll(listOf(enroll1, enroll2, enroll3))
    }
}
