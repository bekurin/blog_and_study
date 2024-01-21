package settlement.core.service

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.stereotype.Service
import java.time.YearMonth

@Service
class SettlementService(
    private val jobLauncher: JobLauncher,
    private val settlementJob: Job,
) {
    fun settlements(settlementYearMonth: YearMonth): JobExecution {
        val jobParameters = JobParametersBuilder()
            .addString("settlementYearMonth", settlementYearMonth.toString())
            .toJobParameters()
        return jobLauncher.run(settlementJob, jobParameters)
    }
}
