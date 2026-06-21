package enterprise.login.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val props: AppProperties,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/login",
                        "/login/options",
                        "/webauthn/**",
                        "/oauth2/device_authorization",
                        "/oauth2/token",
                        "/h2-console/**",
                        "/error",
                    ).permitAll()
                    .requestMatchers("/oauth2/device-authorizations/**").authenticated()
                    .anyRequest().authenticated()
            }
            .webAuthn { passkeys ->
                passkeys
                    .rpName(props.webauthn.rpName)
                    .rpId(props.webauthn.rpId)
                    .allowedOrigins(*props.webauthn.allowedOrigins.toTypedArray())
            }
            .oauth2ResourceServer { rs ->
                rs.jwt { jwt ->
                    jwt.decoder(jwtDecoder())
                }
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .csrf { csrf ->
                csrf.ignoringRequestMatchers(
                    "/oauth2/device_authorization",
                    "/oauth2/device-authorizations/**",
                    "/oauth2/token",
                    "/webauthn/**",
                    "/login",
                    "/login/options",
                    "/api/**",
                )
            }
            .headers { headers ->
                headers.frameOptions { it.deny() }
            }

        return http.build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder =
        NimbusJwtDecoder.withJwkSetUri(props.jwt.jwksUri).build()
}
