package core.serverdrivenui.adapter.out.persistence

import core.serverdrivenui.adapter.out.persistence.repository.BrawlerJpaRepository
import core.serverdrivenui.application.port.out.BrawlerRepository
import core.serverdrivenui.domain.model.Brawler
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Primary
@Repository
class BrawlerRepositoryAdapter(
    private val brawlerJpaRepository: BrawlerJpaRepository,
) : BrawlerRepository {

    override fun findAll(): List<Brawler> {
        return brawlerJpaRepository.findAll().map { it.toDomain() }
    }

    override fun findByNameContaining(name: String): List<Brawler> {
        return brawlerJpaRepository.findByBrawlerNameContainingIgnoreCase(name).map { it.toDomain() }
    }

    override fun findByTier(tier: String): List<Brawler> {
        return brawlerJpaRepository.findByTier(tier).map { it.toDomain() }
    }

    override fun findByNameContainingAndTier(name: String, tier: String): List<Brawler> {
        return brawlerJpaRepository.findByBrawlerNameContainingIgnoreCaseAndTier(name, tier).map { it.toDomain() }
    }

    override fun findById(brawlerId: String): Brawler? {
        return brawlerJpaRepository.findByBrawlerId(brawlerId)?.toDomain()
    }
}
