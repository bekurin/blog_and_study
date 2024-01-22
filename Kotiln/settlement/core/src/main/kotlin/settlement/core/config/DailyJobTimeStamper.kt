package settlement.core.config

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersIncrementer
import org.springframework.batch.core.JobParametersInvalidException
import java.time.LocalDateTime

class DailyJobTimeStamper : JobParametersIncrementer {
    override fun getNext(parameters: JobParameters?): JobParameters {
        parameters ?: throw JobParametersInvalidException("job parameters must not be null.")
        return JobParametersBuilder(parameters)
            .addString("executionAt", LocalDateTime.now().toString())
            .toJobParameters()
    }
}
