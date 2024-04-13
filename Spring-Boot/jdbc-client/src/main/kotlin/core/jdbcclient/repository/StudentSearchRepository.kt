package core.jdbcclient.repository

import core.jdbcclient.domain.Student
import java.util.*

interface StudentSearchRepository {
    fun findByPhone(phone: String): Optional<Student>
}
