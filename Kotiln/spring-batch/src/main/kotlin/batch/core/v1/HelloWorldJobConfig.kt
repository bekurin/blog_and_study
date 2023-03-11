package batch.core.v1

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class HelloWorldJobConfig: DefaultBatchConfiguration() {
    @Value("\${spring.batch.job.name}")
    private lateinit var jobName: String
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun helloWorldJob(helloWorldStep: Step): Job {
        log.info("job start... jobName={}", jobName)
        return JobBuilder("helloWorldJob", jobRepository())
            .start(helloWorldStep)
            .build()
    }

    @Bean
    fun helloWorldStep(helloWorldTasklet: Tasklet): Step {
        log.info("step start...")
        return StepBuilder("helloWorldStep", jobRepository())
            .tasklet(helloWorldTasklet, transactionManager)
            .build()

    }

    @Bean
    fun helloWorldTasklet(): Tasklet {
        log.info("tasklet start...")
        return Tasklet { contribution, chunkContext ->
            println("Hello World Spring Batch")
            RepeatStatus.FINISHED
        }
    }
}