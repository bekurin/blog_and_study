package com.example.exercise06

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Exercise06Application

/**
 * config를 설정할 때에 적절한 역할에 따라 분리하는 것이 좋다.
 * ex)
 * -> userDetailsService, PasswordEncoder
 * -> webConfig
 * -> webSecurityConfig 등등등
 */
fun main(args: Array<String>) {
    runApplication<Exercise06Application>(*args)
}
