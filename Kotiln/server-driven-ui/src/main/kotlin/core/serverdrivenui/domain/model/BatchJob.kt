package core.serverdrivenui.domain.model

import java.time.LocalDateTime

data class BatchJob(
    val jobId: String,
    val jobName: String,
    val status: BatchStatus,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,
)

enum class BatchStatus {
    PENDING,
    RUNNING,
    SUCCESS,
    FAILED,
}
