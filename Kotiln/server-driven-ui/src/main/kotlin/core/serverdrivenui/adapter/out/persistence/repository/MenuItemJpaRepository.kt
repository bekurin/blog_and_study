package core.serverdrivenui.adapter.out.persistence.repository

import core.serverdrivenui.adapter.out.persistence.entity.MenuItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MenuItemJpaRepository : JpaRepository<MenuItemEntity, Long> {
    fun findByComponentId(componentId: Long): List<MenuItemEntity>
}
