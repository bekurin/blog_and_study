package enterprise.login.security.webauthn

import enterprise.login.domain.WebAuthnCredentialEntity
import enterprise.login.domain.WebAuthnCredentialRepository
import org.springframework.security.web.webauthn.api.*
import org.springframework.security.web.webauthn.management.UserCredentialRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.Base64

@Repository
class CredentialRepositoryImpl(
    private val credentialRepository: WebAuthnCredentialRepository,
) : UserCredentialRepository {

    override fun findByCredentialId(credentialId: Bytes): CredentialRecord? {
        val id = Base64.getUrlEncoder().withoutPadding().encodeToString(credentialId.bytes)
        return credentialRepository.findById(id).orElse(null)?.toCredentialRecord()
    }

    override fun findByUserId(userId: Bytes): List<CredentialRecord> {
        val id = String(userId.bytes)
        return credentialRepository.findAllByUserId(id).map { it.toCredentialRecord() }
    }

    override fun save(credentialRecord: CredentialRecord) {
        val credId = Base64.getUrlEncoder().withoutPadding().encodeToString(credentialRecord.credentialId.bytes)
        val existing = credentialRepository.findById(credId).orElse(null)
        if (existing != null) {
            existing.signCount = credentialRecord.signatureCount
            existing.backupState = credentialRecord.isBackupState
            existing.lastUsedAt = Instant.now()
            credentialRepository.save(existing)
        } else {
            credentialRepository.save(credentialRecord.toEntity())
        }
    }

    override fun delete(credentialId: Bytes) {
        val id = Base64.getUrlEncoder().withoutPadding().encodeToString(credentialId.bytes)
        credentialRepository.deleteById(id)
    }

    private fun CredentialRecord.toEntity(): WebAuthnCredentialEntity {
        val credId = Base64.getUrlEncoder().withoutPadding().encodeToString(credentialId.bytes)
        val userId = String(userEntityUserId.bytes)
        val transportStr = transports?.joinToString(",") { it.value }
        return WebAuthnCredentialEntity(
            credentialId = credId,
            userId = userId,
            publicKeyCose = publicKey.bytes,
            signCount = signatureCount,
            transports = transportStr,
            uvInitialized = isUvInitialized,
            backupEligible = isBackupEligible,
            backupState = isBackupState,
        )
    }

    private fun WebAuthnCredentialEntity.toCredentialRecord(): CredentialRecord {
        val credIdBytes = Bytes(Base64.getUrlDecoder().decode(credentialId))
        val userIdBytes = Bytes(userId.toByteArray())
        val transportSet: Set<AuthenticatorTransport> = transports
            ?.split(",")
            ?.filter { it.isNotBlank() }
            ?.map { AuthenticatorTransport.valueOf(it.uppercase()) }
            ?.toSet()
            ?: emptySet()
        val publicKeyCose = PublicKeyCose { publicKeyCose }

        return ImmutableCredentialRecord.builder()
            .credentialType(PublicKeyCredentialType.PUBLIC_KEY)
            .credentialId(credIdBytes)
            .userEntityUserId(userIdBytes)
            .publicKey(publicKeyCose)
            .signatureCount(signCount)
            .transports(transportSet)
            .uvInitialized(uvInitialized)
            .backupEligible(backupEligible)
            .backupState(backupState)
            .attestationObject(null)
            .attestationClientDataJSON(null)
            .created(createdAt)
            .lastUsed(lastUsedAt ?: createdAt)
            .label(nickname ?: credentialId.take(8))
            .build()
    }
}
