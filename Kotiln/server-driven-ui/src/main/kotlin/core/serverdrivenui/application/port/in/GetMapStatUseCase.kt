package core.serverdrivenui.application.port.`in`

import core.serverdrivenui.domain.model.MapStat

interface GetMapStatUseCase {
    fun getAllMaps(mode: String? = null): List<MapStat>
    fun getMapById(mapId: String): MapStat?
}
