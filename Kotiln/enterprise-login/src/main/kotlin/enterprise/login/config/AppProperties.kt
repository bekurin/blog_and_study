package enterprise.login.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.DefaultValue

@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val jwt: JwtProperties,
    val refreshToken: RefreshTokenProperties,
    val webauthn: WebAuthnProperties,
    val deviceAuthorization: DeviceAuthorizationProperties,
) {
    data class JwtProperties(
        val issuer: String,
        val audience: String,
        val accessTokenExpirySeconds: Long,
        val kmsKeyId: String,
        val jwksUri: String,
    )

    data class RefreshTokenProperties(
        @DefaultValue("7") val expiryDays: Long,
    )

    data class WebAuthnProperties(
        val rpId: String,
        val rpName: String,
        val allowedOrigins: List<String>,
    )

    data class DeviceAuthorizationProperties(
        @DefaultValue("600") val expiresIn: Int,
        @DefaultValue("5") val interval: Int,
        val baseUri: String,
    )
}
