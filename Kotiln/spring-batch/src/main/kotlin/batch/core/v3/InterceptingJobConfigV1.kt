package batch.core.v3

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class InterceptingJobConfigV1 : DefaultBatchConfiguration() {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean(name = ["interceptingJob"])
    fun interceptingJob(interceptingStep: Step): Job {
        return JobBuilder("interceptingJob", jobRepository())
            .listener(MyInterceptingJobListenerV1())
            .start(interceptingStep)
            .build()
    }

    @JobScope
    @Bean
    fun interceptingStep(interceptingTasklet: Tasklet): Step {
        log.info("step start...")
        return StepBuilder("interceptingStep", jobRepository())
            .tasklet(interceptingTasklet, transactionManager)
            .build()
    }

    @StepScope
    @Bean
    fun interceptingTasklet(): Tasklet {
        log.info("tasklet start...")
        return Tasklet { contribution, chunkContext ->
            println("do something...")
            RepeatStatus.FINISHED
        }
    }
}

class MyInterceptingJobListenerV1 : JobExecutionListener {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun beforeJob(jobExecution: JobExecution) {
        log.info("[beforeJob] prepare job...")
    }

    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status == BatchStatus.COMPLETED) {
            log.info("[afterJob] job is done. status={}", jobExecution.status)
        }
        if (jobExecution.status == BatchStatus.FAILED) {
            throw RuntimeException()
        }
        log.info("uncaught status...status={}", jobExecution.status)
    }
}
