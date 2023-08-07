package web.webfluxplayground.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .cors { corsSpec -> corsSpec.disable() }
            .csrf { csrfSpec -> csrfSpec.disable() }
            .formLogin { formLoginSpec -> formLoginSpec.disable() }
            .logout { logoutSpec -> logoutSpec.disable() }
            .authorizeExchange { exchange ->
                exchange.apply {
                    matchers(ServerWebExchangeMatchers.pathMatchers("/api/v1/admin/**")).hasAuthority("admin")
                    matchers(ServerWebExchangeMatchers.pathMatchers("/api/**")).hasAuthority("user")
                    anyExchange().permitAll()
                }
            }
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
