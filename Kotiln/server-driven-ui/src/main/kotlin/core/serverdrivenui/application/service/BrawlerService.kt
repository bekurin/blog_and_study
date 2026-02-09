package core.serverdrivenui.application.service

import core.serverdrivenui.application.port.`in`.GetBrawlerUseCase
import core.serverdrivenui.application.port.out.BrawlerRepository
import core.serverdrivenui.domain.model.Brawler
import org.springframework.stereotype.Service

@Service
class BrawlerService(
    private val brawlerRepository: BrawlerRepository,
) : GetBrawlerUseCase {

    override fun getAllBrawlers(name: String?, tier: String?): List<Brawler> =
        when {
            name != null && tier != null -> brawlerRepository.findByNameContainingAndTier(name, tier)
            name != null -> brawlerRepository.findByNameContaining(name)
            tier != null -> brawlerRepository.findByTier(tier)
            else -> brawlerRepository.findAll()
        }

    override fun getBrawlerById(brawlerId: String): Brawler? = brawlerRepository.findById(brawlerId)
}
