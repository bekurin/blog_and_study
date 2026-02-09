package core.serverdrivenui.domain.model

data class Brawler(
    val brawlerId: String,
    val brawlerName: String,
    val winRate: Double,
    val pickRate: Double,
    val tier: String,
    val totalPick: Long,
)
