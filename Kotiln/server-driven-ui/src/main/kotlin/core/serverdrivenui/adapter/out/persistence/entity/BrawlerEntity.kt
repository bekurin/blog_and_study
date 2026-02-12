package core.serverdrivenui.adapter.out.persistence.entity

import core.serverdrivenui.domain.model.Brawler
import jakarta.persistence.*

@Entity
@Table(name = "brawler")
class BrawlerEntity(
    @Column(name = "brawler_id", unique = true, nullable = false, length = 100)
    val brawlerId: String,

    @Column(name = "brawler_name", nullable = false)
    val brawlerName: String,

    @Column(name = "win_rate", nullable = false)
    val winRate: Double,

    @Column(name = "pick_rate", nullable = false)
    val pickRate: Double,

    @Column(nullable = false, length = 20)
    val tier: String,

    @Column(name = "total_pick", nullable = false)
    val totalPick: Long = 0,
) : BaseEntity() {
    fun toDomain(): Brawler = Brawler(
        brawlerId = brawlerId,
        brawlerName = brawlerName,
        winRate = winRate,
        pickRate = pickRate,
        tier = tier,
        totalPick = totalPick,
    )

    companion object {
        fun from(domain: Brawler): BrawlerEntity = BrawlerEntity(
            brawlerId = domain.brawlerId,
            brawlerName = domain.brawlerName,
            winRate = domain.winRate,
            pickRate = domain.pickRate,
            tier = domain.tier,
            totalPick = domain.totalPick,
        )
    }
}
