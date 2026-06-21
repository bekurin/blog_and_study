package enterprise.login.security.webauthn

import enterprise.login.domain.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class WebAuthnUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found: $username")
        val authorities = user.roles.map { SimpleGrantedAuthority(it) }
        return User.builder()
            .username(user.email)
            .password("")
            .authorities(authorities)
            .build()
    }
}
