package batch.core.common.scheduler

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class HelloWorldJobScheduler {

    @Autowired
    private lateinit var helloWorldJob: Job

    @Autowired
    private lateinit var jobLauncher: JobLauncher

    @Scheduled(cron = "0 0/1 * * * *")
    fun helloWorldJobRun() {
        println("scheduled!")
        val jobParameters = JobParameters(
            mapOf("requestTime" to JobParameter(System.currentTimeMillis(), Long::class.java))
        )
        jobLauncher.run(helloWorldJob, jobParameters)
    }
}