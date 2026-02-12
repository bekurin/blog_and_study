package core.serverdrivenui.adapter.out.persistence.repository

import core.serverdrivenui.adapter.out.persistence.entity.LayoutEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LayoutJpaRepository : JpaRepository<LayoutEntity, Long> {
    fun findByLayoutKey(layoutKey: String): LayoutEntity?
}
