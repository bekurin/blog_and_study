package core.jdbcclient.repository

import core.jdbcclient.domain.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student, Long>, StudentSearchRepository {
}
