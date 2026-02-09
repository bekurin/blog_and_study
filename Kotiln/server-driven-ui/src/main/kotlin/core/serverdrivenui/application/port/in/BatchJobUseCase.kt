package core.serverdrivenui.application.port.`in`

import core.serverdrivenui.domain.model.BatchJob

interface BatchJobUseCase {
    fun getAllJobs(): List<BatchJob>
    fun getJobById(jobId: String): BatchJob?
    fun restartJob(jobId: String): BatchJob
}
