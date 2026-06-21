package enterprise.login.api

import enterprise.login.domain.WebAuthnCredentialEntity
import enterprise.login.domain.WebAuthnCredentialRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@RestController
@RequestMapping("/api/webauthn/credentials")
class WebAuthnCredentialController(
    private val credentialRepository: WebAuthnCredentialRepository,
) {

    @GetMapping
    fun list(@AuthenticationPrincipal jwt: Jwt): List<CredentialDto> =
        credentialRepository.findAllByUserId(jwt.subject ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)).map { it.toDto() }

    @PatchMapping("/{id}")
    fun updateNickname(
        @PathVariable id: String,
        @RequestBody body: UpdateNicknameRequest,
        @AuthenticationPrincipal jwt: Jwt,
    ): CredentialDto {
        val credential = findOwned(id, jwt.subject ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED))
        credential.nickname = body.nickname
        return credentialRepository.save(credential).toDto()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable id: String,
        @AuthenticationPrincipal jwt: Jwt,
    ) {
        val userId = jwt.subject ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        val count = credentialRepository.countByUserId(userId)
        if (count <= 1) throw ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete last credential")
        val credential = findOwned(id, userId)
        credentialRepository.delete(credential)
    }

    private fun findOwned(credentialId: String, userId: String): WebAuthnCredentialEntity {
        val credential = credentialRepository.findById(credentialId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        if (credential.userId != userId) throw ResponseStatusException(HttpStatus.FORBIDDEN)
        return credential
    }

    private fun WebAuthnCredentialEntity.toDto() = CredentialDto(
        id = credentialId,
        nickname = nickname,
        transports = transports?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
        createdAt = createdAt,
        lastUsedAt = lastUsedAt,
    )
}

data class CredentialDto(
    val id: String,
    val nickname: String?,
    val transports: List<String>,
    val createdAt: Instant,
    val lastUsedAt: Instant?,
)

data class UpdateNicknameRequest(val nickname: String)
