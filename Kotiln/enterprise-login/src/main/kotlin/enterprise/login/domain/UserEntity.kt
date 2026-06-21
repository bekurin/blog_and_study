package enterprise.login.domain

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val displayName: String,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    val roles: Set<String> = setOf("ROLE_USER"),

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),
)
