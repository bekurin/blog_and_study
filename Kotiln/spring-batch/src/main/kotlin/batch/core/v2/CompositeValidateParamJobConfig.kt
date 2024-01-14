package batch.core.v2

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.JobParametersValidator
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.job.CompositeJobParametersValidator
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CompositeValidateParamJobConfig : DefaultBatchConfiguration() {
    @Bean
    fun compositeValidateParamJob(): CompositeJobParametersValidator {
        // optional keys 가 선언된 경우 required, optional 이외에 선언된 job parameter가 있다면 예외가 발생한다.
        val defaultJobParametersValidator = DefaultJobParametersValidator(
            arrayOf("jobName"), arrayOf("file")
        )
        defaultJobParametersValidator.afterPropertiesSet()

        return CompositeJobParametersValidator().apply {
            setValidators(listOf(ParameterValidator(), defaultJobParametersValidator))
        }
    }
}

class ParameterValidator : JobParametersValidator {
    override fun validate(parameters: JobParameters?) {
        parameters?.let { jobParameters ->
            val fileName = jobParameters.getString("fileName")
                ?: throw JobParametersInvalidException("fileName parameter is mission.")
            if (fileName.endsWith("csv").not()) {
                throw JobParametersInvalidException("fileName parameter does not use the csv file extension.")
            }
        }
    }

}
