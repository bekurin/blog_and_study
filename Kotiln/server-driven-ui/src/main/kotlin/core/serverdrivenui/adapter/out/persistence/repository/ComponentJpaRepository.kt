package core.serverdrivenui.adapter.out.persistence.repository

import core.serverdrivenui.adapter.out.persistence.entity.ComponentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ComponentJpaRepository : JpaRepository<ComponentEntity, Long>
