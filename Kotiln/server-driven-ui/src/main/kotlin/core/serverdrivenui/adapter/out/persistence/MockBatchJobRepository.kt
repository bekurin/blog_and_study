package core.serverdrivenui.adapter.out.persistence

import core.serverdrivenui.application.port.out.BatchJobRepository
import core.serverdrivenui.domain.model.BatchJob
import core.serverdrivenui.domain.model.BatchStatus
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Repository
class MockBatchJobRepository : BatchJobRepository {

    private val jobs = ConcurrentHashMap<String, BatchJob>().apply {
        put("job-001", BatchJob(
            jobId = "job-001",
            jobName = "맵 통계 수집",
            status = BatchStatus.SUCCESS,
            startTime = LocalDateTime.of(2025, 2, 9, 10, 0, 0),
            endTime = LocalDateTime.of(2025, 2, 9, 10, 3, 24),
        ))
        put("job-002", BatchJob(
            jobId = "job-002",
            jobName = "브롤러 승률 계산",
            status = BatchStatus.SUCCESS,
            startTime = LocalDateTime.of(2025, 2, 9, 10, 5, 0),
            endTime = LocalDateTime.of(2025, 2, 9, 10, 8, 15),
        ))
        put("job-003", BatchJob(
            jobId = "job-003",
            jobName = "조합 분석",
            status = BatchStatus.FAILED,
            startTime = LocalDateTime.of(2025, 2, 9, 10, 10, 0),
            endTime = LocalDateTime.of(2025, 2, 9, 10, 12, 30),
        ))
        put("job-004", BatchJob(
            jobId = "job-004",
            jobName = "데이터 정리",
            status = BatchStatus.PENDING,
            startTime = null,
            endTime = null,
        ))
    }

    override fun findAll(): List<BatchJob> = jobs.values.toList()

    override fun findById(jobId: String): BatchJob? = jobs[jobId]

    override fun save(job: BatchJob): BatchJob {
        jobs[job.jobId] = job
        return job
    }
}
