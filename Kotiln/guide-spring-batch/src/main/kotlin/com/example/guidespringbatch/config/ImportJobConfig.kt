package com.example.guidespringbatch.config

import com.example.guidespringbatch.domain.CustomerUpdate
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.file.transform.LineTokenizer
import org.springframework.batch.item.file.transform.PatternMatchingCompositeLineTokenizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ImportJobConfig : DefaultBatchConfiguration() {

    @Bean
    fun job(): Job {
        return JobBuilder("importJob", jobRepository())
            .incrementer(RunIdIncrementer())
            .start(importCustomerUpdates())
            .next(importTransactions())
            .next(applyTransactions())
            .next(generateStatements(null))
            .build()
    }

    @Bean
    fun importCustomerUpdates(): Step {
        return StepBuilder("importCustomerUpdates", jobRepository())
            .chunk<CustomerUpdate, CustomerUpdate>(100)
            .reader(customerUpdateItemReader(null))
            .processor(customerValidatingItemProcessor(null))
            .writer(customerUpdateItemWriter())
            .build()
    }

    @Bean
    fun customerUpdatesLineTokenizer(): LineTokenizer {
        val recordType1 = DelimitedLineTokenizer().apply {
            setNames("recordId", "customerId", "firstName", "middleName", "lastName")
            afterPropertiesSet()
        }

        val recordType2 = DelimitedLineTokenizer().apply {
            setNames("recordId", "customerId", "address1", "city", "state", "portalCode")
            afterPropertiesSet()
        }

        val recordType3 = DelimitedLineTokenizer().apply {
            setNames(
                "recordId",
                "customerId",
                "emailAddress",
                "homePhone",
                "cellPhone",
                "workPhone",
                "notificationPreference"
            )
            afterPropertiesSet()
        }

        val tokenizers = mapOf("1*" to recordType1, "2*" to recordType2, "3*" to recordType3)
        return PatternMatchingCompositeLineTokenizer().apply {
            setTokenizers(tokenizers)
        }
    }

    @Bean
    fun customerUpdateFieldSetMapper(): FieldSetMapper<CustomerUpdate> {
        FieldSetMapper { fieldSet ->
            when(fieldSet) {
                1 -> CustomerUpdate(

                )
            }
        }
    }
}
