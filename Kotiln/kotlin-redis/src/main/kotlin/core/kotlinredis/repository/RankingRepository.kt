package core.kotlinredis.repository

import com.fasterxml.jackson.databind.ObjectMapper
import core.kotlinredis.repository.RedisKey.RANKING
import jakarta.annotation.PostConstruct
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

enum class RedisKey(val key: String, private val description: String) {
    RANKING("RANKING", "랭킹 기능 구현을 위한 Redis 키 값")
}

data class Member(
    val id: String,
    var nickname: String,
    var point: Point,
) {
    fun update(nickname: String = this.nickname, point: Point = this.point): Member {
        var update = this.copy()
        update.nickname = nickname
        update.point = point
        return update
    }
}

data class Point(
    val score: Double,
)

@Component
class PostConstruct(
    private val rankingRepository: RankingRepository,
) {
    @PostConstruct
    fun init() {
        val entity = Member("1", "Bob", Point(1234.0))
        rankingRepository.save(entity)
        rankingRepository.save(Member("2", "Sam", Point(234.0)))
        rankingRepository.save(Member("3", "John", Point(34.0)))
        val update = entity.update(point = Point(12345.0))
        rankingRepository.update(entity, update)
    }
}

@Repository
class RankingRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {
    private val rankingSet: ZSetOperations<String, String> by lazy { redisTemplate.opsForZSet() }

    fun save(entity: Member) {
        rankingSet.add(RANKING.key, objectMapper.writeValueAsString(entity), entity.point.score)
    }

    fun findByScoreRange(range: LongRange): List<Member> {
        val result = rankingSet.range(RANKING.key, range.first, range.last) ?: return listOf<Member>()
        return result.map { item ->
            objectMapper.readValue(item, Member::class.java)
        }.toList()
    }

    fun update(origin: Member, updated: Member) {
        delete(origin)
        save(updated)
    }

    fun delete(entity: Member) {
        val count = rankingSet.remove(RANKING.key, objectMapper.writeValueAsString(entity))
    }
}