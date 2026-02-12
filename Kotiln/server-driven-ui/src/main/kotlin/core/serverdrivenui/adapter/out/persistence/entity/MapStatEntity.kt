package core.serverdrivenui.adapter.out.persistence.entity

import core.serverdrivenui.domain.model.MapStat
import jakarta.persistence.*

@Entity
@Table(name = "map_stat")
class MapStatEntity(
    @Column(name = "map_id", unique = true, nullable = false, length = 100)
    val mapId: String,

    @Column(name = "map_name", nullable = false)
    val mapName: String,

    @Column(nullable = false, length = 100)
    val mode: String,

    @Column(name = "total_games", nullable = false)
    val totalGames: Long = 0,
) : BaseEntity() {
    fun toDomain(): MapStat = MapStat(
        mapId = mapId,
        mapName = mapName,
        mode = mode,
        totalGames = totalGames,
    )

    companion object {
        fun from(domain: MapStat): MapStatEntity = MapStatEntity(
            mapId = domain.mapId,
            mapName = domain.mapName,
            mode = domain.mode,
            totalGames = domain.totalGames,
        )
    }
}
