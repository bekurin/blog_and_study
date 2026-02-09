package core.serverdrivenui.adapter.`in`.web

import core.serverdrivenui.application.port.`in`.BatchJobUseCase
import core.serverdrivenui.domain.model.BatchJob
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/batch/jobs")
class BatchJobController(
    private val batchJobUseCase: BatchJobUseCase,
) {

    @GetMapping
    fun getAllJobs(): List<BatchJob> = batchJobUseCase.getAllJobs()

    @GetMapping("/{jobId}")
    fun getJobById(@PathVariable jobId: String): ResponseEntity<BatchJob> {
        val job = batchJobUseCase.getJobById(jobId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(job)
    }

    @PostMapping("/{jobId}/restart")
    fun restartJob(@PathVariable jobId: String): ResponseEntity<BatchJob> =
        try {
            ResponseEntity.ok(batchJobUseCase.restartJob(jobId))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
}
