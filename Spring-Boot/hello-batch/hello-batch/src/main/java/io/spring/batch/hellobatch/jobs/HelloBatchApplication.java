package io.spring.batch.hellobatch.jobs;

import io.spring.batch.hellobatch.batch.DailyJobTimeStamper;
import io.spring.batch.hellobatch.batch.JobLoggerListener;
import io.spring.batch.hellobatch.batch.ParameterValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@EnableBatchProcessing
@SpringBootApplication
public class HelloBatchApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public CompositeJobParametersValidator validator() {
		CompositeJobParametersValidator validator = new CompositeJobParametersValidator();

		DefaultJobParametersValidator defaultJobParametersValidator = new DefaultJobParametersValidator(
				new String[]{"fileName"},
				new String[]{"name", "currentDate"});

		defaultJobParametersValidator.afterPropertiesSet();

		validator.setValidators(
				Arrays.asList(new ParameterValidator(), defaultJobParametersValidator));

		return validator;
	}

	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("basicJob")
				.start(step1())
				.validator(validator())
				.incrementer(new DailyJobTimeStamper())
				.listener(new JobLoggerListener())
				.build();
	}

	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("step1")
				.tasklet(helloWorldTasklet(null, null))
				.build();
	}

	@StepScope
	@Bean
	public Tasklet helloWorldTasklet(
			@Value("#{jobParameters['name']}") String name,
			@Value("#{jobParameters['fileName']}") String fileName) {
		return ((contribution, chunkContext) -> {
			System.out.println(String.format("Hello, %s", name));
			System.out.println(String.format("fileName = %s", fileName));

			return RepeatStatus.FINISHED;
		});
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloBatchApplication.class, args);
	}

}
