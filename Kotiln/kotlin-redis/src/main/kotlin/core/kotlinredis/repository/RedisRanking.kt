package core.kotlinredis.repository

import com.fasterxml.jackson.databind.ObjectMapper
import core.kotlinredis.repository.RedisKey.RANKING
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

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
    fun init() {
        rankingRepository.save(Member("1", "Bob", Point(1234.0)))
        rankingRepository.save(Member("2", "Sam", Point(234.0)))
        rankingRepository.save(Member("3", "John", Point(34.0)))
    }
}

@Repository
class RankingRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {
    private val rankingSet: ZSetOperations<String, String> by lazy { redisTemplate.opsForZSet() }

    fun save(entity: Member): Boolean? {
        return rankingSet.add(RANKING.key, objectMapper.writeValueAsString(entity), entity.point.score)
    }

    fun findAllByScoreRange(range: LongRange): List<Member> {
        val result = rankingSet.reverseRange(RANKING.key, range.first, range.last) ?: listOf()
        return result.map { item ->
            objectMapper.readValue(item, Member::class.java)
        }.toList()
    }

    fun findByEntity(entity: Member): Long? {
        return rankingSet.reverseRank(RANKING.key, objectMapper.writeValueAsString(entity))
    }

    fun findAllByRanking(count: Long) {
        rankingSet.scan(RANKING.key, ScanOptions.scanOptions().count(count).build())
    }

    fun update(origin: Member, updated: Member): Member {
        delete(origin)
        save(updated)
        return updated
    }

    fun delete(entity: Member): Long {
        return rankingSet.remove(RANKING.key, objectMapper.writeValueAsString(entity)) ?: 0
    }
}

@Service
class RankingService(
    private val rankingRepository: RankingRepository,
) {
    fun save(entity: Member): Member {
        val result = rankingRepository.save(entity)
        println("result = ${result}")
        if ((result == null) || (result == false)) {
            throw RuntimeException("${entity.id}를 저장할 수 없습니다")
        }
        return entity
    }

    fun findAllByScoreRange(start: Long, end: Long): List<Member> {
        return rankingRepository.findAllByScoreRange(LongRange(start, end))
    }

    fun findByEntity(entity: Member): Long {
        return rankingRepository.findByEntity(entity)
            ?: throw RuntimeException("${entity.id}를 찾을 수 없습니다")
    }

    fun findAllByRanking(count: Long) {
        return rankingRepository.findAllByRanking(count)
    }

    fun update(entity: Member, score: Double): Member {
        return rankingRepository.update(
            entity,
            entity.update(entity.nickname, Point(score))
        )
    }

    fun delete(entity: Member): Long {
        val count = rankingRepository.delete(entity)
        return if (count == 0L) throw RuntimeException("${entity.id}를 가진 회원을 찾지 못했습니다") else count
    }
}