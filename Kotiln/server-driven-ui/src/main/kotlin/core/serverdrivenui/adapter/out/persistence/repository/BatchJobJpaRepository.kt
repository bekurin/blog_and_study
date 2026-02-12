package core.serverdrivenui.adapter.out.persistence.repository

import core.serverdrivenui.adapter.out.persistence.entity.BatchJobEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BatchJobJpaRepository : JpaRepository<BatchJobEntity, Long> {
    fun findByJobId(jobId: String): BatchJobEntity?
}
