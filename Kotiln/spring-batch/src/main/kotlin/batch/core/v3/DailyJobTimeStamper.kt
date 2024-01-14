package batch.core.v3

import batch.core.v1.AbstractHelloWorldJob
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersIncrementer
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import java.util.*


class DailyJobTimeStamper : JobParametersIncrementer {
    override fun getNext(parameters: JobParameters?): JobParameters {
        parameters ?: throw JobParametersInvalidException("job parameters must not be null.")
        return JobParametersBuilder(parameters)
            .addDate("currentDate", Date())
            .toJobParameters()
    }
}

class DailyJobConfig() : AbstractHelloWorldJob() {
    override fun helloWorldJob(helloWorldStep: Step): Job {
        return JobBuilder("helloWorldJob", jobRepository())
            .preventRestart()
            // DailyJobTimeStamper 추가
            .incrementer(DailyJobTimeStamper())
            .start(helloWorldStep)
            .build()
    }
}


