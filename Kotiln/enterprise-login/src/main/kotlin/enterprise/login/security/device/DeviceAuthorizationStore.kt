package enterprise.login.security.device

enum class DeviceGrantStatus { PENDING, APPROVED, DENIED }

data class DeviceGrant(
    val deviceCode: String,
    val userCode: String,
    val userId: String? = null,
    val status: DeviceGrantStatus = DeviceGrantStatus.PENDING,
)

interface DeviceAuthorizationStore {
    fun create(): DeviceGrant
    fun findByDeviceCode(deviceCode: String): DeviceGrant?
    fun findByUserCode(userCode: String): DeviceGrant?
    fun approve(userCode: String, userId: String): Boolean
    fun deny(userCode: String): Boolean
    fun consume(deviceCode: String): DeviceGrant?
}
