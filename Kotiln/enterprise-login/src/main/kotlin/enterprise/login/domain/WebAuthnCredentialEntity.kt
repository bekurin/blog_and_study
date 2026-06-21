package enterprise.login.domain

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "webauthn_credentials")
class WebAuthnCredentialEntity(
    @Id
    @Column(name = "credential_id", length = 1024)
    val credentialId: String,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Lob
    @Column(name = "public_key_cose", nullable = false)
    val publicKeyCose: ByteArray,

    @Column(name = "sign_count", nullable = false)
    var signCount: Long = 0,

    @Column(name = "transports")
    val transports: String? = null,

    @Column(name = "uv_initialized", nullable = false)
    val uvInitialized: Boolean = false,

    @Column(name = "backup_eligible", nullable = false)
    val backupEligible: Boolean = false,

    @Column(name = "backup_state", nullable = false)
    var backupState: Boolean = false,

    @Column(name = "nickname")
    var nickname: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "last_used_at")
    var lastUsedAt: Instant? = null,
)
