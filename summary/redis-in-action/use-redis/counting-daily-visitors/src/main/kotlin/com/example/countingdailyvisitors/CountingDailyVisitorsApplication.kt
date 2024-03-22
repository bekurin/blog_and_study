package com.example.countingdailyvisitors

import com.example.countingdailyvisitors.domain.User
import com.example.countingdailyvisitors.domain.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CountingDailyVisitorsApplication(
    private val userRepository: UserRepository
) {
    @PostConstruct
    fun createUser() {
        val userPrefix = "user"
        val userCount = 100_000
        val domain = listOf("gmail.com", "naver.com", "daum.net", "redis.com", "sample1.com")
        val users = (0..userCount).map {
            val domainIndex = it % domain.size
            val email = "$userPrefix$it@${domain[domainIndex]}"
            User(email)
        }
        userRepository.saveAll(users)
    }
}

fun main(args: Array<String>) {
    runApplication<CountingDailyVisitorsApplication>(*args)
}
