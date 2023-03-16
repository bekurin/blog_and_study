package batch.core.common.controller

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JobLauncherController(
    private val jobLauncher: JobLauncher,
    private val interceptingJob: Job
) {
    @GetMapping("/intercepting-job")
    fun runInterceptingJob() {
        jobLauncher.run(interceptingJob, JobParameters())
    }

}