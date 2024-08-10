package core.kotlinredis.repository

import org.springframework.data.redis.connection.BitFieldSubCommands
import org.springframework.data.redis.connection.BitFieldSubCommands.BitFieldSet
import org.springframework.data.redis.connection.BitFieldSubCommands.BitFieldType
import org.springframework.data.redis.connection.BitFieldSubCommands.Offset
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class BitMapRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun executeBitfieldCommand(key: String, bitfieldValues: List<BitfieldValue>): List<Long> {
        if (bitfieldValues.isEmpty()) {
            return emptyList()
        }

        val subCommands = bitfieldValues.map { segment ->
            BitFieldSet.create(
                BitFieldType.INT_32,
                Offset.offset(segment.offset),
                segment.value
            )
        }
        return redisTemplate.opsForValue()
            .bitField(
                key,
                BitFieldSubCommands.create(
                    *subCommands.toTypedArray()
                )
            ) ?: throw RuntimeException("redis 명령어 실행 중에 예상치 못한 에러가 발생했습니다")
    }
}

object BitMapConstant {
    val REFERENCE_TIME = LocalDateTime.of(2024, 1, 1, 0, 0, 0)
    val TIME_QUARTER_SECOND = 600
    val BITFIELD_SEGMENT_SIZE = 32
}


data class TimeRange(
    private val startAt: LocalDateTime,
    private val endAt: LocalDateTime
) {
    init {
        if (endAt < startAt) {
            throw IllegalArgumentException("종료 시간이 시작 시간보다 클 수 없습니다")
        }
    }

    fun indexFromReferenceTime(): TimeIndex {
        val startAtFromReference = Duration.between(BitMapConstant.REFERENCE_TIME, startAt)
        val endAtFromReference = Duration.between(BitMapConstant.REFERENCE_TIME, endAt)

        val startIndex = startAtFromReference.toSeconds() / BitMapConstant.TIME_QUARTER_SECOND
        val endIndex = endAtFromReference.toSeconds() / BitMapConstant.TIME_QUARTER_SECOND
        return TimeIndex(startIndex, endIndex)
    }
}

data class TimeIndex(
    val start: Long,
    val end: Long
) {
    fun calculateBitfieldValues(): List<BitfieldValue> {
        var currentFrom = start
        val result = mutableListOf<BitfieldValue>()
        val totalBits = end - start + 1
        val fullSegments = totalBits / BitMapConstant.BITFIELD_SEGMENT_SIZE
        val remainingBits = totalBits % BitMapConstant.BITFIELD_SEGMENT_SIZE

        for (i in 0 until fullSegments) {
            result.add(BitfieldValue(currentFrom, Long.MAX_VALUE))
            currentFrom += BitMapConstant.BITFIELD_SEGMENT_SIZE
        }

        if (remainingBits > 0) {
            val bitValue = (1L shl remainingBits.toInt()) - 1L
            result.add(BitfieldValue(currentFrom, bitValue))
        }
        return result
    }
}

data class BitfieldValue(
    val offset: Long,
    val value: Long
)

fun main() {
    val timeIndex = TimeIndex(100, 105)
    println()
}
