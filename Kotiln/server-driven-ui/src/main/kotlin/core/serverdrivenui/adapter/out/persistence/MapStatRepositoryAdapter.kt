package core.serverdrivenui.adapter.out.persistence

import core.serverdrivenui.adapter.out.persistence.repository.MapStatJpaRepository
import core.serverdrivenui.application.port.out.MapStatRepository
import core.serverdrivenui.domain.model.MapStat
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Primary
@Repository
class MapStatRepositoryAdapter(
    private val mapStatJpaRepository: MapStatJpaRepository,
) : MapStatRepository {

    override fun findAll(): List<MapStat> {
        return mapStatJpaRepository.findAll().map { it.toDomain() }
    }

    override fun findByMode(mode: String): List<MapStat> {
        return mapStatJpaRepository.findByMode(mode).map { it.toDomain() }
    }

    override fun findById(mapId: String): MapStat? {
        return mapStatJpaRepository.findByMapId(mapId)?.toDomain()
    }
}
