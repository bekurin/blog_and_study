package core.jdbcclient.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class Student(
    name: String,
    phone: String
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0
        protected set

    var name: String = name
        protected set

    var phone: String = phone
        protected set
}
