package com.example.countingdailyvisitors.v3

import com.example.countingdailyvisitors.Constant.V3.PAGE_VISIT_KEY
import com.example.countingdailyvisitors.Constant.V3.UNIQUE_VISIT_EVENT
import com.example.countingdailyvisitors.Constant.V3.UNIQUE_VISIT_KEY
import org.springframework.data.redis.connection.RedisStringCommands.BitOperation.AND
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WeeklyVisitServiceV3(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun visit(userId: Long, now: LocalDate) {
        redisTemplate.executePipelined { connection ->
            connection.stringCommands().incr("$PAGE_VISIT_KEY:$now".toByteArray())
            connection.stringCommands().setBit("$UNIQUE_VISIT_KEY:$now".toByteArray(), userId, true)
        }
    }

    fun getWeeklyPageVisitor(now: LocalDate): Long {
        val keys = (0..7L).map { now.plusDays(it) }
            .map { "$PAGE_VISIT_KEY:$it" }
        val dailyVisitCounts = redisTemplate.opsForValue().multiGet(keys)
        return dailyVisitCounts?.sumOf { dailyVisitCount -> dailyVisitCount.toLong() } ?: 0L
    }

    fun getWeeklyUniqueVisitCount(now: LocalDate): Long {
        val keys = (0..7L).map { now.plusDays(it) }
            .map {
                "$UNIQUE_VISIT_KEY:$it".toByteArray()
            }
        val uniqueVisitCount = redisTemplate.execute { connection ->
            connection.stringCommands().bitOp(
                AND,
                UNIQUE_VISIT_EVENT,
                *keys.toTypedArray()
            )
            connection.stringCommands().bitCount(UNIQUE_VISIT_EVENT)
        } ?: 0L
        return uniqueVisitCount
    }
}
