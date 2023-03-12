package batch.core.common.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class User(
    id: UInt,
    username: String,
    email: String,
    updatedAt: LocalDateTime = LocalDateTime.now(),
    createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    var id: UInt = id
        protected set

    @Column(nullable = false)
    var username: String = username
        protected set

    @Column(nullable = false, length = 30)
    var email: String = email
        protected set

    var updatedAt: LocalDateTime = updatedAt
        protected set

    @Column(updatable = false)
    var createdAt: LocalDateTime = createdAt
        protected set
}