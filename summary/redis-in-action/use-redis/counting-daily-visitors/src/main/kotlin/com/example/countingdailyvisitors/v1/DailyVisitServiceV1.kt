package com.example.countingdailyvisitors.v1

import com.example.countingdailyvisitors.Constant.V1.PAGE_VISIT_KEY
import com.example.countingdailyvisitors.Constant.V1.UNIQUE_VISIT_KEY
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class DailyVisitServiceV1(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun visit(userId: Long) {
        redisTemplate.opsForValue().increment(PAGE_VISIT_KEY)
        redisTemplate.opsForValue().setBit(UNIQUE_VISIT_KEY, userId, true)
    }

    fun getPageVisitor(): Int {
        return redisTemplate.opsForValue().get(PAGE_VISIT_KEY)?.toInt() ?: 0
    }

    fun getUniqueVisitor(): Long {
        val result = redisTemplate.execute { connection ->
            connection.stringCommands().bitCount(UNIQUE_VISIT_KEY.toByteArray())
        } ?: 0
        return result
    }
}
