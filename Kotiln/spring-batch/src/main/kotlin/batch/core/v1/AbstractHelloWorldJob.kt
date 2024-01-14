package batch.core.v1

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
abstract class AbstractHelloWorldJob : DefaultBatchConfiguration() {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean()
    fun helloWorldJob(helloWorldStep: Step): Job {
        return JobBuilder("helloWorldJob", jobRepository())
            .preventRestart()
            .incrementer(RunIdIncrementer())
            .start(helloWorldStep)
            .build()
    }

    @Bean
    fun helloWorldStep(
        helloWorldTasklet: Tasklet,
    ): Step {
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
