package enterprise.login.security.device

import com.fasterxml.jackson.annotation.JsonProperty
import enterprise.login.config.AppProperties
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class DeviceAuthorizationController(
    private val store: DeviceAuthorizationStore,
    private val props: AppProperties,
) {

    /**
     * RFC 8628 §3.1 — 디바이스 코드 발급 (웹 클라이언트가 호출)
     */
    @PostMapping(
        "/oauth2/device_authorization",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun issueDeviceAuthorization(): DeviceAuthorizationResponse {
        val grant = store.create()
        val verificationUriComplete = "${props.deviceAuthorization.baseUri}?user_code=${grant.userCode}"
        return DeviceAuthorizationResponse(
            deviceCode = grant.deviceCode,
            userCode = grant.userCode,
            verificationUri = props.deviceAuthorization.baseUri,
            verificationUriComplete = verificationUriComplete,
            expiresIn = props.deviceAuthorization.expiresIn,
            interval = props.deviceAuthorization.interval,
        )
    }

    /**
     * 모바일 앱이 QR 스캔 후 승인/거부 (인증 필요)
     *
     * PATCH /oauth2/device-authorizations/{userCode}
     * {"approved": true}  → 204 승인
     * {"approved": false} → 204 거부
     */
    @PatchMapping(
        "/oauth2/device-authorizations/{userCode}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateDeviceAuthorization(
        @PathVariable userCode: String,
        @RequestBody body: DeviceAuthorizationPatch,
        @AuthenticationPrincipal jwt: Jwt,
    ) {
        val userId = jwt.subject ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        val success = if (body.approved) {
            store.approve(userCode, userId)
        } else {
            store.deny(userCode)
        }
        if (!success) throw ResponseStatusException(HttpStatus.NOT_FOUND, "user_code not found or expired")
    }
}

data class DeviceAuthorizationResponse(
    @JsonProperty("device_code") val deviceCode: String,
    @JsonProperty("user_code") val userCode: String,
    @JsonProperty("verification_uri") val verificationUri: String,
    @JsonProperty("verification_uri_complete") val verificationUriComplete: String,
    @JsonProperty("expires_in") val expiresIn: Int,
    @JsonProperty("interval") val interval: Int,
)

data class DeviceAuthorizationPatch(val approved: Boolean)
