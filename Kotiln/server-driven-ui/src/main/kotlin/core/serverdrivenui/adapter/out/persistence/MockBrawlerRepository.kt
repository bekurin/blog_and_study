package core.serverdrivenui.adapter.out.persistence

import core.serverdrivenui.application.port.out.BrawlerRepository
import core.serverdrivenui.domain.model.Brawler
import org.springframework.stereotype.Repository

@Repository
class MockBrawlerRepository : BrawlerRepository {

    private val brawlers = listOf(
        Brawler("brawler-001", "셸리", 0.52, 0.08, "2T", 45000),
        Brawler("brawler-002", "니타", 0.54, 0.06, "2T", 38000),
        Brawler("brawler-003", "콜트", 0.48, 0.07, "3T", 42000),
        Brawler("brawler-004", "불", 0.51, 0.05, "2T", 31000),
        Brawler("brawler-005", "제시", 0.53, 0.06, "2T", 36000),
        Brawler("brawler-006", "브록", 0.49, 0.05, "3T", 29000),
        Brawler("brawler-007", "다이나마이크", 0.47, 0.04, "3T", 25000),
        Brawler("brawler-008", "보", 0.55, 0.07, "1T", 43000),
        Brawler("brawler-009", "틱", 0.46, 0.04, "4T", 22000),
        Brawler("brawler-010", "8비트", 0.50, 0.05, "2T", 30000),
        Brawler("brawler-011", "엠즈", 0.52, 0.06, "2T", 35000),
        Brawler("brawler-012", "엘 프리모", 0.53, 0.07, "2T", 40000),
        Brawler("brawler-013", "바르리", 0.48, 0.04, "3T", 24000),
        Brawler("brawler-014", "포코", 0.51, 0.05, "2T", 28000),
        Brawler("brawler-015", "로사", 0.56, 0.08, "1T", 48000),
        Brawler("brawler-016", "리코", 0.49, 0.05, "3T", 27000),
        Brawler("brawler-017", "데릴", 0.54, 0.06, "1T", 37000),
        Brawler("brawler-018", "페니", 0.50, 0.05, "2T", 29000),
        Brawler("brawler-019", "칼", 0.47, 0.04, "4T", 21000),
        Brawler("brawler-020", "재키", 0.52, 0.06, "2T", 34000),
    )

    override fun findAll(): List<Brawler> = brawlers

    override fun findByNameContaining(name: String): List<Brawler> =
        brawlers.filter { it.brawlerName.contains(name, ignoreCase = true) }

    override fun findByTier(tier: String): List<Brawler> =
        brawlers.filter { it.tier == tier }

    override fun findByNameContainingAndTier(name: String, tier: String): List<Brawler> =
        brawlers.filter { it.brawlerName.contains(name, ignoreCase = true) && it.tier == tier }

    override fun findById(brawlerId: String): Brawler? =
        brawlers.find { it.brawlerId == brawlerId }
}
