package com.example.distributedlock

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class DistributedLockService(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun acquireLock(key: String, expireSeconds: Long): Boolean {
        val isAcquireLock = redisTemplate.opsForValue()
            .setIfAbsent(key, "1", Duration.ofSeconds(expireSeconds))
        return isAcquireLock ?: false
    }

    fun leaseLock(key: String): Boolean {
        return redisTemplate.delete(key)
    }
}
