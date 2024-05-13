package backend.authentication.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/css/**", "/images/**", "/js/**", "/*/icon-*").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { form ->
                form.loginPage("/login").permitAll()
            }
            .build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val userDetails = User.withUsername("user").password("123").roles("USER").build()
        return InMemoryUserDetailsManager(userDetails)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        // Default bcryptPasswordEncoder
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    // @Bean 
    fun customPasswordEncoder(): PasswordEncoder {
        val encodingId = "pbkdf2"
        val encoderMap = mapOf(encodingId to Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8())
        val delegatingPasswordEncoder = DelegatingPasswordEncoder(encodingId, encoderMap)
        return delegatingPasswordEncoder
    }
}
