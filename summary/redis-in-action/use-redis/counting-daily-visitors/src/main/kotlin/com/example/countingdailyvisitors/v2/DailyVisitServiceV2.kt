package com.example.countingdailyvisitors.v2

import com.example.countingdailyvisitors.Constant.V2.PAGE_VISIT_KEY
import com.example.countingdailyvisitors.Constant.V2.UNIQUE_VISIT_KEY
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class DailyVisitServiceV2(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun visit(userId: Long) {
        redisTemplate.executePipelined { connection ->
            connection.stringCommands().incr(PAGE_VISIT_KEY)
            connection.stringCommands().setBit(UNIQUE_VISIT_KEY, userId, true)
        }
    }

    fun getPageVisitor(): Int {
        return redisTemplate.opsForValue().get(PAGE_VISIT_KEY)?.toInt() ?: 0
    }

    fun getUniqueVisitor(): Long {
        val result = redisTemplate.execute { connection ->
            connection.stringCommands().bitCount(UNIQUE_VISIT_KEY)
        } ?: 0
        return result
    }
}
