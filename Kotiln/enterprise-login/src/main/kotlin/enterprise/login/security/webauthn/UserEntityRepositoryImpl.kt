package enterprise.login.security.webauthn

import enterprise.login.domain.UserEntity
import enterprise.login.domain.UserRepository
import org.springframework.security.web.webauthn.api.Bytes
import org.springframework.security.web.webauthn.api.ImmutablePublicKeyCredentialUserEntity
import org.springframework.security.web.webauthn.api.PublicKeyCredentialUserEntity
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository
import org.springframework.stereotype.Repository

@Repository
class UserEntityRepositoryImpl(
    private val userRepository: UserRepository,
) : PublicKeyCredentialUserEntityRepository {

    override fun findById(id: Bytes): PublicKeyCredentialUserEntity? {
        val userId = String(id.bytes)
        return userRepository.findById(userId).orElse(null)?.toPublicKeyCredentialUserEntity()
    }

    override fun findByUsername(username: String): PublicKeyCredentialUserEntity? =
        userRepository.findByEmail(username)?.toPublicKeyCredentialUserEntity()

    override fun save(userEntity: PublicKeyCredentialUserEntity) {
        val userId = String(userEntity.id.bytes)
        if (!userRepository.existsById(userId)) {
            userRepository.save(
                UserEntity(
                    id = userId,
                    email = userEntity.name,
                    displayName = userEntity.displayName ?: userEntity.name,
                )
            )
        }
    }

    override fun delete(id: Bytes) {
        val userId = String(id.bytes)
        userRepository.deleteById(userId)
    }

    private fun UserEntity.toPublicKeyCredentialUserEntity(): PublicKeyCredentialUserEntity =
        ImmutablePublicKeyCredentialUserEntity.builder()
            .id(Bytes(id.toByteArray()))
            .name(email)
            .displayName(displayName)
            .build()
}
