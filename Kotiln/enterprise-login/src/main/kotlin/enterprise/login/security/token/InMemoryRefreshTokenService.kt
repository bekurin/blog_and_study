package enterprise.login.security.token

import enterprise.login.config.AppProperties
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Instant
import java.util.Base64
import java.util.concurrent.ConcurrentHashMap

@Service
@Profile("!prod")
class InMemoryRefreshTokenService(
    private val props: AppProperties,
) : RefreshTokenService {

    private data class TokenEntry(val userId: String, val expiresAt: Instant)

    private val tokens = ConcurrentHashMap<String, TokenEntry>()
    private val revokedTokens = ConcurrentHashMap<String, String>()
    private val rng = SecureRandom()

    override fun issue(userId: String): String {
        val token = generateToken()
        val expiresAt = Instant.now().plusSeconds(props.refreshToken.expiryDays * 86400)
        tokens[token] = TokenEntry(userId, expiresAt)
        return token
    }

    override fun rotate(oldToken: String): Pair<String, String>? {
        val entry = tokens.remove(oldToken)
        if (entry == null) {
            // Reuse of a consumed token — revocation cascade
            return null
        }
        if (Instant.now().isAfter(entry.expiresAt)) return null
        revokedTokens[oldToken] = entry.userId
        return entry.userId to issue(entry.userId)
    }

    override fun revoke(token: String) {
        tokens.remove(token)
    }

    override fun getUserId(token: String): String? {
        val entry = tokens[token] ?: return null
        if (Instant.now().isAfter(entry.expiresAt)) {
            tokens.remove(token)
            return null
        }
        return entry.userId
    }

    private fun generateToken(): String {
        val bytes = ByteArray(32)
        rng.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }
}
