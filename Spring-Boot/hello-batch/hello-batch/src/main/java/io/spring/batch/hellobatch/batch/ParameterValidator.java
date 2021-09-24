package io.spring.batch.hellobatch.batch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class ParameterValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String filename = parameters.getString("fileName");

        if (!StringUtils.hasText(filename)) {
            throw new JobParametersInvalidException("fileName parameter is missing!");
        } else if (!StringUtils.endsWithIgnoreCase(filename, "csv")) {
            throw new JobParametersInvalidException("fileName parameter dose not use the csv file extension");
        }
    }
}
