package enterprise.login.security.device

import enterprise.login.config.AppProperties
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

class DeviceAuthorizationStoreTest {

    private val props = AppProperties(
        jwt = AppProperties.JwtProperties("https://auth.example.com", "https://api.example.com", 900, "alias/key", "http://localhost/.well-known/jwks.json"),
        refreshToken = AppProperties.RefreshTokenProperties(7),
        webauthn = AppProperties.WebAuthnProperties("example.com", "Example", listOf("https://example.com")),
        deviceAuthorization = AppProperties.DeviceAuthorizationProperties(600, 5, "https://example.com/oauth2/device"),
    )

    private lateinit var store: DeviceAuthorizationStore

    @BeforeEach
    fun setup() {
        store = InMemoryDeviceAuthorizationStore(props)
    }

    @Test
    fun `create returns grant with non-blank device and user codes`() {
        val grant = store.create()

        assertTrue(grant.deviceCode.isNotBlank())
        assertTrue(grant.userCode.isNotBlank())
        assertEquals(DeviceGrantStatus.PENDING, grant.status)
        assertNull(grant.userId)
    }

    @Test
    fun `user code has XXXX-XXXX format`() {
        val grant = store.create()

        val parts = grant.userCode.split("-")
        assertEquals(2, parts.size)
        assertEquals(4, parts[0].length)
        assertEquals(4, parts[1].length)
    }

    @Test
    fun `findByDeviceCode returns null for unknown device code`() {
        assertNull(store.findByDeviceCode("nonexistent"))
    }

    @Test
    fun `approve returns false when user code not found`() {
        assertFalse(store.approve("ABCD-EFGH", "user-123"))
    }

    @Test
    fun `approve sets status to APPROVED`() {
        val grant = store.create()

        val result = store.approve(grant.userCode, "user-123")

        assertTrue(result)
        val updated = store.findByDeviceCode(grant.deviceCode)
        assertEquals(DeviceGrantStatus.APPROVED, updated?.status)
        assertEquals("user-123", updated?.userId)
    }

    @Test
    fun `deny sets status to DENIED`() {
        val grant = store.create()

        val result = store.deny(grant.userCode)

        assertTrue(result)
        val updated = store.findByDeviceCode(grant.deviceCode)
        assertEquals(DeviceGrantStatus.DENIED, updated?.status)
    }

    @Test
    fun `consume removes grant after APPROVED`() {
        val grant = store.create()
        store.approve(grant.userCode, "user-123")

        store.consume(grant.deviceCode)

        assertNull(store.findByDeviceCode(grant.deviceCode))
    }

    @Test
    fun `findByUserCode resolves to device grant`() {
        val grant = store.create()

        val found = store.findByUserCode(grant.userCode)

        assertEquals(grant.deviceCode, found?.deviceCode)
    }
}
