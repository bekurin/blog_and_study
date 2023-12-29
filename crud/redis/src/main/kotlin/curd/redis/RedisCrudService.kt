package curd.redis

import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.domain.geo.GeoReference
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisCrudService(
    private val redisTemplate: RedisOperations<String, String>
) {
    fun getLock(key: String, ttlInMillis: Long): Boolean {
        return redisTemplate
            .opsForValue()
            .setIfAbsent(key, "true", Duration.ofMillis(ttlInMillis))
            ?: false
    }

    fun insertPlaces(key: String, places: List<Place>): Long {
        val memberCoordinateMap = places.associate { current ->
            Pair(current.name, current.coordinate.toPoint())
        }
        return redisTemplate
            .opsForGeo()
            .add(key, memberCoordinateMap)
            ?: throw RuntimeException("지점 저장 중에 문제가 발생했습니다.")
    }

    fun search(key: String, point: Point, radius: Distance): List<Place> {
        return redisTemplate
            .opsForGeo()
            .search(key, GeoReference.fromCoordinate(point), radius)
            ?.map { geoResult ->
                val (latitude, longitude) = geoResult.content.point.x.toBigDecimal() to geoResult.content.point.y.toBigDecimal()
                Place(geoResult.content.name, Coordinate(latitude, longitude))
            }
            ?: emptyList()
    }

    fun delete(key: String, member: String): Long {
        return redisTemplate
            .opsForZSet()
            .remove(key, member)
            ?: 0L
    }

    fun update(key: String, member: String, coordinate: Coordinate): Long {
        delete(key, member)
            .takeIf { predicate ->
                predicate != 0L
            } ?: throw RuntimeException("지점 삭제에 실패했습니다.")
        return insertPlaces(key, listOf(Place(member, coordinate)))
    }

}
