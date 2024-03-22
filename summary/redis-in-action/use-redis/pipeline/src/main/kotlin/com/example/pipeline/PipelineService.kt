package com.example.pipeline

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PipelineService(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {
    private val redisKey1 = "BOARDABLE_ROUTE1"
    private val redisKey2 = "BOARDABLE_ROUTE2"
    private val log = LoggerFactory.getLogger(PipelineService::class.java)

    @PostConstruct
    fun init() {
        val now = LocalDate.now()
        val localDates = (1..5L).map { dayToAdds ->
            now.plusDays(dayToAdds)
        }

        redisTemplate.delete(redisKey2)
        val currentTimeMillisForWithoutPipeline = System.currentTimeMillis()
        // 2613ms 2448ms 2363ms 2448ms 2713ms
        saveToRedisWithoutPipeline(localDates)
        log.info("running without pipeline took {}ms", System.currentTimeMillis() - currentTimeMillisForWithoutPipeline)

        redisTemplate.delete(redisKey1)
        val currentTimeMillisForWithPipeline = System.currentTimeMillis()
        // 321ms 327ms 331ms 392ms 319ms
        saveToRedisWithPipeline(localDates)
        log.info("running with pipeline took {}ms", System.currentTimeMillis() - currentTimeMillisForWithPipeline)
    }

    fun saveToRedisWithoutPipeline(localDates: List<LocalDate>): Int {
        localDates.mapIndexed { index, localDate ->
            val helloDto = HelloDto("$localDate", "hello$index")
            redisTemplate.opsForHash<String, String>()
                .put(
                    redisKey2,
                    localDate.toString(),
                    objectMapper.writeValueAsString(helloDto)
                )
        }
        return localDates.size
    }

    fun saveToRedisWithPipeline(localDates: List<LocalDate>): Int {
        redisTemplate.executePipelined { connection ->
            localDates.mapIndexed { index, localDate ->
                val helloDto = HelloDto("$localDate", "hello$index")
                connection.hashCommands()
                    .hSet(
                        redisKey1.toByteArray(),
                        localDate.toString().toByteArray(),
                        objectMapper.writeValueAsBytes(helloDto)
                    )
            }
            return@executePipelined null
        }
        return localDates.size
    }

    data class HelloDto(
        val createdAt: String,
        val message: String,
        val massiveString: String
    ) {
        constructor(createdAt: String, message: String) : this(
            createdAt = createdAt,
            message = message,
            massiveString = String(CharArray(1024 * 512))
        )
    }
}
