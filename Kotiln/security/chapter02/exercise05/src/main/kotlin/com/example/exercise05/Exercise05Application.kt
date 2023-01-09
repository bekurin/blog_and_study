package com.example.exercise05

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Exercise05Application

/**
 * authenticationManager 를 사용하여 authenticationProvider 를 임의로 설정할 수 있다.
 * AuthenticationManager -> AuthenticationProvider -> (UserDetailsService, PasswordEncoder) 의 계층으로 구성된다.
 * 따라서 provider 를 임의로 설정한다는 것은 PasswordEncoder와 UserDetailsService를 한번에 설정하겠다는 것이다.
 */
fun main(args: Array<String>) {
    runApplication<Exercise05Application>(*args)
}
