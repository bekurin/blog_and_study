package settlement.core.controller

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.YearMonth

@RestController
@RequestMapping("/v1")
class SettlementController(
    private val jobLauncher: JobLauncher,
    private val settlementJob: Job
) {
    @PostMapping("/settlements")
    fun createSettlements(
        @RequestBody request: SettlementCreationRequest
    ): ResponseEntity<JobExecution> {
        val execution = jobLauncher.run(settlementJob, JobParameters(request.toJobParameters()))
        return ResponseEntity.status(HttpStatus.CREATED).body(execution)
    }
}

data class SettlementCreationRequest(
    val settlementYearMonth: YearMonth
) {
    fun toJobParameters(): Map<String, JobParameter<YearMonth>> {
        return mapOf(
            "settlementYearMonth" to JobParameter(
                settlementYearMonth,
                YearMonth::class.java
            )
        )
    }
}
