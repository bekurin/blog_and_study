package core.serverdrivenui.application.port.`in`

import core.serverdrivenui.domain.model.Brawler

interface GetBrawlerUseCase {
    fun getAllBrawlers(name: String? = null, tier: String? = null): List<Brawler>
    fun getBrawlerById(brawlerId: String): Brawler?
}
