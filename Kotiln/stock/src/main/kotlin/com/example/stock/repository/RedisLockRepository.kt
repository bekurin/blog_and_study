package com.example.stock.repository

import com.example.stock.exception.InternalServerError
import com.example.stock.support.Constant.REDIS_LOCK
import com.example.stock.support.ErrorCode.REDIS_COMMAND_ERROR
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class RedisLockRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun acquireLock(id: Long): Boolean {
        return redisTemplate
            .opsForValue()
            .setIfAbsent("$REDIS_LOCK:$id", UUID.randomUUID().toString(), Duration.ofMillis(3000L))
            ?: throw InternalServerError(REDIS_COMMAND_ERROR)
    }

    fun releaseLock(id: Long): Boolean {
        return redisTemplate
            .delete("$REDIS_LOCK:$id")
    }
}
