package com.example.exercise03

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Exercise03Application

/**
 * Spring Security 5.7.1 버전 이후에는
 * AuthenticationManagerBuilder 를 사용하여 userService, passwordEncoder를 사용할 필요 없이
 * 빈으로 설정해주면 자동으로 설정해준다.
 * AuthenticationManagerBuilder를 사용하고 싶다면 빈으로 등록하여 사용하면 된다.
 */
fun main(args: Array<String>) {
    runApplication<Exercise03Application>(*args)
}
