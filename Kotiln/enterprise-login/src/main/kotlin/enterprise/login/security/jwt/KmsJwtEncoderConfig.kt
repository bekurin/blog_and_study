package enterprise.login.security.jwt

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import enterprise.login.config.AppProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder

/**
 * Development profile uses a generated in-memory RSA key.
 * Production must supply a KMS-backed JwtEncoder bean instead.
 */
@Configuration
@Profile("!prod")
class KmsJwtEncoderConfig {

    @Bean
    fun devRsaKey(): RSAKey {
        val keyPair = java.security.KeyPairGenerator.getInstance("RSA").apply {
            initialize(2048)
        }.generateKeyPair()
        return RSAKey.Builder(keyPair.public as java.security.interfaces.RSAPublicKey)
            .privateKey(keyPair.private as java.security.interfaces.RSAPrivateKey)
            .keyID(DevKeyConstants.KID)
            .build()
    }

    @Bean
    fun jwtEncoder(rsaKey: RSAKey): JwtEncoder {
        val jwkSource = ImmutableJWKSet<SecurityContext>(JWKSet(rsaKey))
        return NimbusJwtEncoder(jwkSource)
    }

    @Bean
    fun activeKid(rsaKey: RSAKey): String = rsaKey.keyID
}

object DevKeyConstants {
    const val KID = "dev-key-01"
}
