package com.example.exercise01

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Exercise01Application

/**
 * UserDetailsService: 사용자의 세부 정보를 얻는 방식을 기술한 인터페이스 (데이터베이스를 사용하면 query를 실행하는 처리, 세션을 사용하면 세션 정보를 가져오는 처리)
 * UserDetails: 사용자 인증 정보를 기술한 인터페이스 (username(PK), password, authorities 등)
 * username을 고유하게 받는 것이 아니라 pk(id)값을 고유하게 받아 처리할 수 없을까?
 */
fun main(args: Array<String>) {
    runApplication<Exercise01Application>(*args)
}
