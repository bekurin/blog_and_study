package batch.core.v2

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.JobParametersValidator

class FileParamValidator: JobParametersValidator {
    override fun validate(parameters: JobParameters?) {
        val fileName = parameters?.getString("fileName") ?: ""

        if (!fileName.endsWith("csv")) {
            throw JobParametersInvalidException("This is not csv file!")
        }
    }
}