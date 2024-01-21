package batch.core.v3

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.Step
import org.springframework.batch.core.annotation.AfterJob
import org.springframework.batch.core.annotation.BeforeJob
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.listener.JobListenerFactoryBean
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


// annotation based after, before job processing
//@Configuration
class InterceptingJobConfigV2 : DefaultBatchConfiguration() {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean(name = ["interceptingJob"])
    fun interceptingJob(interceptingStep: Step): Job {
        return JobBuilder("interceptingJob", jobRepository())
            .listener(JobListenerFactoryBean.getListener(MyInterceptingJobListenerV2()))
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

class MyInterceptingJobListenerV2 {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)


    @BeforeJob
    fun beforeJob(jobExecution: JobExecution) {
        log.info("[beforeJob] prepare job...")
    }

    @AfterJob
    fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status == BatchStatus.COMPLETED) {
            log.info("[afterJob] job is done. status={}", jobExecution.status)
        }
        if (jobExecution.status == BatchStatus.FAILED) {
            throw RuntimeException()
        }
        log.info("uncaught status...status={}", jobExecution.status)
    }
}
