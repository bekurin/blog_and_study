package core.serverdrivenui.application.port.out

import core.serverdrivenui.domain.model.BatchJob

interface BatchJobRepository {
    fun findAll(): List<BatchJob>
    fun findById(jobId: String): BatchJob?
    fun save(job: BatchJob): BatchJob
}
