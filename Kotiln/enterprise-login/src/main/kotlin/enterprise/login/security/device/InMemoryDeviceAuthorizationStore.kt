package enterprise.login.security.device

import enterprise.login.config.AppProperties
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.Instant
import java.util.Base64
import java.util.concurrent.ConcurrentHashMap

@Component
@Profile("!prod")
class InMemoryDeviceAuthorizationStore(
    private val props: AppProperties,
) : DeviceAuthorizationStore {

    private data class Entry(val grant: DeviceGrant, val expiresAt: Instant)

    private val grants = ConcurrentHashMap<String, Entry>()
    private val userCodeIndex = ConcurrentHashMap<String, String>()
    private val rng = SecureRandom()

    override fun create(): DeviceGrant {
        val deviceCode = generateCode(32)
        val userCode = generateUserCode()
        val grant = DeviceGrant(deviceCode = deviceCode, userCode = userCode)
        val expiresAt = Instant.now().plusSeconds(props.deviceAuthorization.expiresIn.toLong())
        grants[deviceCode] = Entry(grant, expiresAt)
        userCodeIndex[userCode] = deviceCode
        return grant
    }

    override fun findByDeviceCode(deviceCode: String): DeviceGrant? {
        val entry = grants[deviceCode] ?: return null
        if (Instant.now().isAfter(entry.expiresAt)) {
            grants.remove(deviceCode)
            userCodeIndex.remove(entry.grant.userCode)
            return null
        }
        return entry.grant
    }

    override fun findByUserCode(userCode: String): DeviceGrant? {
        val deviceCode = userCodeIndex[userCode] ?: return null
        return findByDeviceCode(deviceCode)
    }

    override fun approve(userCode: String, userId: String): Boolean {
        val deviceCode = userCodeIndex[userCode] ?: return false
        val entry = grants[deviceCode] ?: return false
        grants[deviceCode] = entry.copy(grant = entry.grant.copy(userId = userId, status = DeviceGrantStatus.APPROVED))
        return true
    }

    override fun deny(userCode: String): Boolean {
        val deviceCode = userCodeIndex[userCode] ?: return false
        val entry = grants[deviceCode] ?: return false
        grants[deviceCode] = entry.copy(grant = entry.grant.copy(status = DeviceGrantStatus.DENIED))
        return true
    }

    override fun consume(deviceCode: String): DeviceGrant? {
        val entry = grants.remove(deviceCode) ?: return null
        userCodeIndex.remove(entry.grant.userCode)
        return entry.grant
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
}
