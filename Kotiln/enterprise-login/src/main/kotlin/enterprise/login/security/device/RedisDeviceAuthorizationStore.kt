package enterprise.login.security.device

import enterprise.login.config.AppProperties
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.Duration
import java.util.Base64

@Component
@Profile("prod")
class RedisDeviceAuthorizationStore(
    private val redis: StringRedisTemplate,
    private val props: AppProperties,
) : DeviceAuthorizationStore {

    private val rng = SecureRandom()
    private val ttl get() = Duration.ofSeconds(props.deviceAuthorization.expiresIn.toLong())

    override fun create(): DeviceGrant {
        val deviceCode = generateCode(32)
        val userCode = generateUserCode()
        redis.opsForHash<String, String>().putAll(
            grantKey(deviceCode),
            mapOf("userCode" to userCode, "status" to DeviceGrantStatus.PENDING.name)
        )
        redis.expire(grantKey(deviceCode), ttl)
        redis.opsForValue().set(userCodeKey(userCode), deviceCode, ttl)
        return DeviceGrant(deviceCode = deviceCode, userCode = userCode)
    }

    override fun findByDeviceCode(deviceCode: String): DeviceGrant? {
        val data = redis.opsForHash<String, String>().entries(grantKey(deviceCode))
        if (data.isEmpty()) return null
        return DeviceGrant(
            deviceCode = deviceCode,
            userCode = data["userCode"] ?: return null,
            userId = data["userId"],
            status = DeviceGrantStatus.valueOf(data["status"] ?: DeviceGrantStatus.PENDING.name),
        )
    }

    override fun findByUserCode(userCode: String): DeviceGrant? {
        val deviceCode = redis.opsForValue().get(userCodeKey(userCode)) ?: return null
        return findByDeviceCode(deviceCode)
    }

    override fun approve(userCode: String, userId: String): Boolean {
        val deviceCode = redis.opsForValue().get(userCodeKey(userCode)) ?: return false
        redis.opsForHash<String, String>().putAll(
            grantKey(deviceCode),
            mapOf("status" to DeviceGrantStatus.APPROVED.name, "userId" to userId)
        )
        return true
    }

    override fun deny(userCode: String): Boolean {
        val deviceCode = redis.opsForValue().get(userCodeKey(userCode)) ?: return false
        redis.opsForHash<String, String>().put(grantKey(deviceCode), "status", DeviceGrantStatus.DENIED.name)
        return true
    }

    override fun consume(deviceCode: String): DeviceGrant? {
        val grant = findByDeviceCode(deviceCode) ?: return null
        if (grant.status == DeviceGrantStatus.APPROVED) {
            redis.delete(grantKey(deviceCode))
            redis.delete(userCodeKey(grant.userCode))
        }
        return grant
    }

    private fun generateCode(length: Int): String {
        val bytes = ByteArray(length)
        rng.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    private fun generateUserCode(): String {
        val chars = "BCDFGHJKLMNPQRSTVWXZ"
        return (1..8).map { chars[rng.nextInt(chars.length)] }
            .joinToString("")
            .chunked(4)
            .joinToString("-")
    }

    private fun grantKey(deviceCode: String) = "dac:$deviceCode"
    private fun userCodeKey(userCode: String) = "dac:uc:$userCode"
}
