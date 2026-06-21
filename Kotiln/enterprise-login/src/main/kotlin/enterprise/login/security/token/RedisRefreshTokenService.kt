package enterprise.login.security.token

import enterprise.login.config.AppProperties
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Duration
import java.util.Base64

@Service
@Profile("prod")
class RedisRefreshTokenService(
    private val redis: StringRedisTemplate,
    private val props: AppProperties,
) : RefreshTokenService {

    private val rng = SecureRandom()
    private val ttl get() = Duration.ofDays(props.refreshToken.expiryDays)

    override fun issue(userId: String): String {
        val token = generateToken()
        redis.opsForValue().set(tokenKey(token), userId, ttl)
        return token
    }

    override fun rotate(oldToken: String): Pair<String, String>? {
        val userId = consume(oldToken) ?: run {
            revokeAllByToken(oldToken)
            return null
        }
        return userId to issue(userId)
    }

    override fun revoke(token: String) {
        redis.delete(tokenKey(token))
    }

    override fun getUserId(token: String): String? =
        redis.opsForValue().get(tokenKey(token))

    private fun consume(token: String): String? {
        val key = tokenKey(token)
        val userId = redis.opsForValue().get(key) ?: return null
        redis.delete(key)
        redis.opsForValue().set(revokedKey(token), userId, Duration.ofMinutes(30))
        return userId
    }

    private fun revokeAllByToken(token: String) {
        redis.opsForValue().get(revokedKey(token))?.let { userId ->
            redis.delete(userSessionKey(userId))
        }
    }

    private fun generateToken(): String {
        val bytes = ByteArray(32)
        rng.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    private fun tokenKey(token: String) = "rt:$token"
    private fun revokedKey(token: String) = "rt:revoked:$token"
    private fun userSessionKey(userId: String) = "rt:user:$userId"
}
