package batch.core.v2

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.job.CompositeJobParametersValidator
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class ValidateParamJobConfig: DefaultBatchConfiguration() {
    @Value("\${spring.batch.job.name}")
    private lateinit var jobName: String

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun validateParamJob(validateParamStep: Step): Job {
        log.info("job start... jobName={}", jobName)
        return JobBuilder("validateParamJob", jobRepository())
            .incrementer(RunIdIncrementer())
            .validator(multipleValidator())
            .start(validateParamStep)
            .build()
    }

    private fun multipleValidator(): CompositeJobParametersValidator {
        val validators = CompositeJobParametersValidator()
        validators.setValidators(listOf(FileParamValidator()))
        return validators
    }

    @Bean
    fun validateParamStep(validateParamTasklet: Tasklet): Step {
        log.info("step start...")
        return StepBuilder("validateParamStep", jobRepository())
            .tasklet(validateParamTasklet, transactionManager)
            .build()

    }

    @StepScope
    @Bean
    fun validateParamTasklet(@Value("#{jobParameters['fileName']}") fileName: String): Tasklet {
        log.info("tasklet start... fileName={}", fileName)
        return Tasklet { contribution, chunkContext ->
            println("validated param tasklet!")
            RepeatStatus.FINISHED
        }
    }
}