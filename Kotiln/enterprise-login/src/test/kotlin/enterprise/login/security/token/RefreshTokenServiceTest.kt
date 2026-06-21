package enterprise.login.security.token

import enterprise.login.config.AppProperties
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

class RefreshTokenServiceTest {

    private val props = AppProperties(
        jwt = AppProperties.JwtProperties("https://auth.example.com", "https://api.example.com", 900, "alias/key", "http://localhost/.well-known/jwks.json"),
        refreshToken = AppProperties.RefreshTokenProperties(7),
        webauthn = AppProperties.WebAuthnProperties("example.com", "Example", listOf("https://example.com")),
        deviceAuthorization = AppProperties.DeviceAuthorizationProperties(600, 5, "https://example.com/oauth2/device"),
    )

    private lateinit var service: RefreshTokenService

    @BeforeEach
    fun setup() {
        service = InMemoryRefreshTokenService(props)
    }

    @Test
    fun `issue returns a non-blank token`() {
        val token = service.issue("user-123")

        assertTrue(token.isNotBlank())
    }

    @Test
    fun `getUserId returns userId for valid token`() {
        val token = service.issue("user-123")

        assertEquals("user-123", service.getUserId(token))
    }

    @Test
    fun `getUserId returns null for unknown token`() {
        assertNull(service.getUserId("unknown-token"))
    }

    @Test
    fun `rotate returns new token and invalidates old one`() {
        val oldToken = service.issue("user-123")

        val result = service.rotate(oldToken)

        assertNotNull(result)
        assertEquals("user-123", result.first)
        assertTrue(result.second.isNotBlank())
        assertNotEquals(oldToken, result.second)
        assertNull(service.getUserId(oldToken))
    }

    @Test
    fun `rotate returns null when token is already consumed`() {
        val oldToken = service.issue("user-123")
        service.rotate(oldToken)

        val result = service.rotate(oldToken)

        assertNull(result)
    }

    @Test
    fun `revoke invalidates the token`() {
        val token = service.issue("user-123")

        service.revoke(token)

        assertNull(service.getUserId(token))
    }
}
