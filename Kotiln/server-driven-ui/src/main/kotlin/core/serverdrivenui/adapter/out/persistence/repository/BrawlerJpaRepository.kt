package core.serverdrivenui.adapter.out.persistence.repository

import core.serverdrivenui.adapter.out.persistence.entity.BrawlerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BrawlerJpaRepository : JpaRepository<BrawlerEntity, Long> {
    fun findByBrawlerId(brawlerId: String): BrawlerEntity?
    fun findByBrawlerNameContainingIgnoreCase(name: String): List<BrawlerEntity>
    fun findByTier(tier: String): List<BrawlerEntity>
    fun findByBrawlerNameContainingIgnoreCaseAndTier(name: String, tier: String): List<BrawlerEntity>
}
