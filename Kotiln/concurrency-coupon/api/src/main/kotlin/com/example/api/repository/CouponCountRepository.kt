package com.example.api.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class CouponCountRepository(
    val redisTemplate: RedisTemplate<String, String>
) {
    fun count(): Long {
        return redisTemplate
            .opsForValue()
            .increment("coupon_count")
            ?: throw RuntimeException("coupon_count 값이 없습니다.")
    }
}
