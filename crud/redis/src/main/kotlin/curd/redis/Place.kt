package curd.redis

import org.springframework.data.geo.Point
import java.math.BigDecimal

class Place(
    val name: String,
    val coordinate: Coordinate
)

data class Coordinate(
    val latitude: BigDecimal,
    val longitude: BigDecimal
) {
    fun toPoint(): Point {
        return Point(latitude.toDouble(), longitude.toDouble())
    }
}
