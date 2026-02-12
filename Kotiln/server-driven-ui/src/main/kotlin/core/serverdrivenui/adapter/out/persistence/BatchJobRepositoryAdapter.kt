package core.serverdrivenui.adapter.out.persistence

import core.serverdrivenui.adapter.out.persistence.entity.BatchJobEntity
import core.serverdrivenui.adapter.out.persistence.repository.BatchJobJpaRepository
import core.serverdrivenui.application.port.out.BatchJobRepository
import core.serverdrivenui.domain.model.BatchJob
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Primary
@Repository
class BatchJobRepositoryAdapter(
    private val batchJobJpaRepository: BatchJobJpaRepository,
) : BatchJobRepository {

    override fun findAll(): List<BatchJob> {
        return batchJobJpaRepository.findAll().map { it.toDomain() }
    }

    override fun findById(jobId: String): BatchJob? {
        return batchJobJpaRepository.findByJobId(jobId)?.toDomain()
    }

    override fun save(job: BatchJob): BatchJob {
        val entity = batchJobJpaRepository.findByJobId(job.jobId)
            ?.apply { updateFrom(job) }
            ?: BatchJobEntity.from(job)

        return batchJobJpaRepository.save(entity).toDomain()
    }
}
