package core.serverdrivenui.adapter.out.persistence.entity

import core.serverdrivenui.domain.model.BatchJob
import core.serverdrivenui.domain.model.BatchStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "batch_job")
class BatchJobEntity(
    @Column(name = "job_id", unique = true, nullable = false, length = 100)
    val jobId: String,

    @Column(name = "job_name", nullable = false)
    var jobName: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: BatchStatus,

    @Column(name = "start_time")
    var startTime: LocalDateTime? = null,

    @Column(name = "end_time")
    var endTime: LocalDateTime? = null,
) : BaseEntity() {
    fun toDomain(): BatchJob = BatchJob(
        jobId = jobId,
        jobName = jobName,
        status = status,
        startTime = startTime,
        endTime = endTime,
    )

    fun updateFrom(domain: BatchJob) {
        this.jobName = domain.jobName
        this.status = domain.status
        this.startTime = domain.startTime
        this.endTime = domain.endTime
    }

    companion object {
        fun from(domain: BatchJob): BatchJobEntity = BatchJobEntity(
            jobId = domain.jobId,
            jobName = domain.jobName,
            status = domain.status,
            startTime = domain.startTime,
            endTime = domain.endTime,
        )
    }
}
