package core.serverdrivenui.application.port.out

import core.serverdrivenui.domain.model.MapStat

interface MapStatRepository {
    fun findAll(): List<MapStat>
    fun findByMode(mode: String): List<MapStat>
    fun findById(mapId: String): MapStat?
}
