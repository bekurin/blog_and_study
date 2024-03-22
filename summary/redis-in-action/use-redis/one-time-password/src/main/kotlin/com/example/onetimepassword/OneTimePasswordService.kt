package com.example.onetimepassword

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration.ofSeconds
import kotlin.random.Random

@Service
class OneTimePasswordService(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun create(phone: String): String {
        val oneTimePassword = generateSixDigitRandomNumber()
        redisTemplate.opsForValue().set(phone, generateSixDigitRandomNumber(), ofSeconds(60))
        return oneTimePassword
    }

    fun verify(phone: String, oneTimePassword: String): Boolean {
        val foundOneTimePassword = redisTemplate.opsForValue()
            .get(phone)
        return foundOneTimePassword == oneTimePassword
    }

    private fun generateSixDigitRandomNumber(): String {
        return Random.nextInt(100000, 999999 + 1).toString()
    }
}
