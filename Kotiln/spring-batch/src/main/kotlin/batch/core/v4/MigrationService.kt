package batch.core.v4

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.stereotype.Service

@Service
class MigrationService(
    private val jobLauncher: JobLauncher,
    private val migrationJob: Job
) {
    fun migrateSettlements(): JobExecution {
        val jobParameters = JobParametersBuilder()
            .addString("migrate", "userName")
            .toJobParameters()
        return jobLauncher.run(migrationJob, jobParameters)
    }
}
