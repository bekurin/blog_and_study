package core.serverdrivenui.application.service

import core.serverdrivenui.application.port.`in`.BatchJobUseCase
import core.serverdrivenui.application.port.out.BatchJobRepository
import core.serverdrivenui.domain.model.BatchJob
import core.serverdrivenui.domain.model.BatchStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BatchJobService(
    private val batchJobRepository: BatchJobRepository,
) : BatchJobUseCase {

    override fun getAllJobs(): List<BatchJob> = batchJobRepository.findAll()

    override fun getJobById(jobId: String): BatchJob? = batchJobRepository.findById(jobId)

    override fun restartJob(jobId: String): BatchJob {
        val job = batchJobRepository.findById(jobId)
            ?: throw IllegalArgumentException("Job not found: $jobId")

        val restartedJob = job.copy(
            status = BatchStatus.RUNNING,
            startTime = LocalDateTime.now(),
            endTime = null,
        )
        return batchJobRepository.save(restartedJob)
    }
}
