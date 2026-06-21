package enterprise.login.security.token

import com.fasterxml.jackson.annotation.JsonProperty
import enterprise.login.domain.UserRepository
import enterprise.login.security.device.DeviceAuthorizationStore
import enterprise.login.security.device.DeviceGrantStatus
import enterprise.login.security.jwt.JwtTokenService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

private const val GRANT_TYPE_DEVICE_CODE = "urn:ietf:params:oauth:grant-type:device_code"
private const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
private const val REFRESH_TOKEN_COOKIE = "refresh_token"

@RestController
@RequestMapping("/oauth2/token")
class TokenController(
    private val deviceStore: DeviceAuthorizationStore,
    private val refreshTokenService: RefreshTokenService,
    private val jwtTokenService: JwtTokenService,
    private val userRepository: UserRepository,
    private val activeKid: String,
) {

    /**
     * RFC 6749 §4 / RFC 8628 §3.4 — 토큰 발급 및 갱신
     * grant_type: urn:ietf:params:oauth:grant-type:device_code | refresh_token
     */
    @PostMapping(
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun issueToken(
        @RequestParam("grant_type") grantType: String,
        @RequestParam("device_code", required = false) deviceCode: String?,
        @CookieValue(REFRESH_TOKEN_COOKIE, required = false) refreshTokenCookie: String?,
        response: HttpServletResponse,
    ): TokenResponse = when (grantType) {
        GRANT_TYPE_DEVICE_CODE -> handleDeviceCode(deviceCode, response)
        GRANT_TYPE_REFRESH_TOKEN -> handleRefreshToken(refreshTokenCookie, response)
        else -> throw OAuthException("unsupported_grant_type", "Supported: $GRANT_TYPE_DEVICE_CODE, $GRANT_TYPE_REFRESH_TOKEN")
    }

    private fun handleDeviceCode(deviceCode: String?, response: HttpServletResponse): TokenResponse {
        if (deviceCode.isNullOrBlank()) throw OAuthException("invalid_request", "device_code is required")
        val grant = deviceStore.findByDeviceCode(deviceCode) ?: throw OAuthException("expired_token", "Device code expired or not found")
        return when (grant.status) {
            DeviceGrantStatus.PENDING -> throw OAuthException("authorization_pending", "User has not yet approved the request")
            DeviceGrantStatus.DENIED  -> throw OAuthException("access_denied", "User denied the authorization request", HttpStatus.FORBIDDEN)
            DeviceGrantStatus.APPROVED -> {
                deviceStore.consume(deviceCode)
                val userId = grant.userId ?: throw OAuthException("access_denied", httpStatus = HttpStatus.FORBIDDEN)
                val user = userRepository.findById(userId).orElseThrow {
                    OAuthException("access_denied", httpStatus = HttpStatus.FORBIDDEN)
                }
                val accessToken = jwtTokenService.issueAccessToken(user, activeKid)
                val refreshToken = refreshTokenService.issue(userId)
                setRefreshTokenCookie(response, refreshToken)
                TokenResponse(accessToken = accessToken, tokenType = "Bearer", expiresIn = 900)
            }
        }
    }

    private fun handleRefreshToken(cookie: String?, response: HttpServletResponse): TokenResponse {
        if (cookie.isNullOrBlank()) throw OAuthException("invalid_grant", "Refresh token cookie not present")
        val (userId, newRefreshToken) = refreshTokenService.rotate(cookie)
            ?: throw OAuthException("invalid_grant", "Refresh token is expired, revoked, or reused")
        val user = userRepository.findById(userId).orElseThrow {
            OAuthException("invalid_grant", "Associated user not found")
        }
        val accessToken = jwtTokenService.issueAccessToken(user, activeKid)
        setRefreshTokenCookie(response, newRefreshToken)
        return TokenResponse(accessToken = accessToken, tokenType = "Bearer", expiresIn = 900)
    }

    private fun setRefreshTokenCookie(response: HttpServletResponse, token: String) {
        val cookie = Cookie(REFRESH_TOKEN_COOKIE, token).apply {
            isHttpOnly = true
            secure = true
            path = "/oauth2/token"
            maxAge = 7 * 24 * 60 * 60
            setAttribute("SameSite", "Strict")
        }
        response.addCookie(cookie)
    }
}

data class TokenResponse(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("expires_in") val expiresIn: Int,
)
