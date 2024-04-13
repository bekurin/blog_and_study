package core.jdbcclient.repository

import core.jdbcclient.domain.Student
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class StudentSearchRepositoryImpl(
    private val jdbcClient: JdbcClient
) : StudentSearchRepository {
    override fun findByPhone(phone: String): Optional<Student> {
        val sql = "select * from student where phone = :phone"
        return jdbcClient.sql(sql)
            .param("phone", phone)
            .query(Student::class.java)
            .optional()
    }
}
