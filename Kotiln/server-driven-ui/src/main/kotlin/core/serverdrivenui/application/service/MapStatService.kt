package core.serverdrivenui.application.service

import core.serverdrivenui.application.port.`in`.GetMapStatUseCase
import core.serverdrivenui.application.port.out.MapStatRepository
import core.serverdrivenui.domain.model.MapStat
import org.springframework.stereotype.Service

@Service
class MapStatService(
    private val mapStatRepository: MapStatRepository,
) : GetMapStatUseCase {

    override fun getAllMaps(mode: String?): List<MapStat> =
        if (mode != null) {
            mapStatRepository.findByMode(mode)
        } else {
            mapStatRepository.findAll()
        }

    override fun getMapById(mapId: String): MapStat? = mapStatRepository.findById(mapId)
}
