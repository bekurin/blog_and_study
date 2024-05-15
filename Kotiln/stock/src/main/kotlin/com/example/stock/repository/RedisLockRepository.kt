package com.example.stock.repository

import com.example.stock.exception.InternalServerError
import com.example.stock.support.ErrorCode.REDIS_COMMAND_ERROR
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class RedisLockRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun acquireLock(key: String): Boolean {
        return redisTemplate
            .opsForValue()
            .setIfAbsent(key, UUID.randomUUID().toString(), Duration.ofMillis(3000L))
            ?: throw InternalServerError(REDIS_COMMAND_ERROR)
    }

    fun releaseLock(key: String): Boolean {
        return redisTemplate
            .delete(key)
    }
}
