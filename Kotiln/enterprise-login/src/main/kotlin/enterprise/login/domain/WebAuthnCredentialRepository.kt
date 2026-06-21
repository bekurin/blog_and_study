package enterprise.login.domain

import org.springframework.data.jpa.repository.JpaRepository

interface WebAuthnCredentialRepository : JpaRepository<WebAuthnCredentialEntity, String> {
    fun findAllByUserId(userId: String): List<WebAuthnCredentialEntity>
    fun countByUserId(userId: String): Long
}
