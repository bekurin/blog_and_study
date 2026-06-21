package enterprise.login.security.jwt

import com.fasterxml.uuid.Generators
import enterprise.login.config.AppProperties
import enterprise.login.domain.UserEntity
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class JwtTokenService(
    private val jwtEncoder: JwtEncoder,
    private val props: AppProperties,
) {
    private val uuidGenerator = Generators.timeBasedReorderedGenerator()

    fun issueAccessToken(user: UserEntity, kid: String): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer(props.jwt.issuer)
            .subject(user.id)
            .audience(listOf(props.jwt.audience))
            .issuedAt(now)
            .notBefore(now)
            .expiresAt(now.plusSeconds(props.jwt.accessTokenExpirySeconds))
            .id(uuidGenerator.generate().toString())
            .claim("scope", "openid profile email")
            .claim("roles", user.roles.toList())
            .claim("email", user.email)
            .build()

        val header = JwsHeader.with(SignatureAlgorithm.RS256)
            .keyId(kid)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).tokenValue
    }
}
