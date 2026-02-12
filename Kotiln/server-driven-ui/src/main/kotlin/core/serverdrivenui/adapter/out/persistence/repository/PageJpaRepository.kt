package core.serverdrivenui.adapter.out.persistence.repository

import core.serverdrivenui.adapter.out.persistence.entity.PageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PageJpaRepository : JpaRepository<PageEntity, Long> {
    fun findByPageKey(pageKey: String): PageEntity?
}
