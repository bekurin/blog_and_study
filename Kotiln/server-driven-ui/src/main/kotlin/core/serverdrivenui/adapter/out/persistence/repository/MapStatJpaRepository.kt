package core.serverdrivenui.adapter.out.persistence.repository

import core.serverdrivenui.adapter.out.persistence.entity.MapStatEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MapStatJpaRepository : JpaRepository<MapStatEntity, Long> {
    fun findByMapId(mapId: String): MapStatEntity?
    fun findByMode(mode: String): List<MapStatEntity>
}
