package core.serverdrivenui.application.port.out

import core.serverdrivenui.domain.model.Brawler

interface BrawlerRepository {
    fun findAll(): List<Brawler>
    fun findByNameContaining(name: String): List<Brawler>
    fun findByTier(tier: String): List<Brawler>
    fun findByNameContainingAndTier(name: String, tier: String): List<Brawler>
    fun findById(brawlerId: String): Brawler?
}
