package settlement.core.configuration

import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import settlement.core.domain.Company
import settlement.core.domain.settlement.repository.DeliverySettlementRepository
import settlement.core.domain.settlement.repository.PackagingSettlementRepository
import settlement.core.domain.settlement.repository.ShippingSettlementRepository
import settlement.core.domain.snapshot.SettlementSummary
import java.time.YearMonth

@Configuration
class SettlementJobConfiguration(
    private val transactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val deliverySettlementRepository: DeliverySettlementRepository,
    private val packagingSettlementRepository: PackagingSettlementRepository,
    private val shippingSettlementRepository: ShippingSettlementRepository
) : DefaultBatchConfiguration() {
    private val chunkSize = 500

    @Bean
    fun settlementJob(): Job {
        return JobBuilder("settlementJob", jobRepository())
            .start(companyStep())
            .build()
    }

    @Bean
    @JobScope
    fun companyStep(): Step {
        return StepBuilder("companyStep", jobRepository())
            .chunk<Company, SettlementSummary>(chunkSize, transactionManager)
            .reader(readChunkedDeliverySettlement())
            .processor(processorSettlement())
            .writer(writeSettlementSummary())
            .build()
    }

    @Bean
    fun readChunkedDeliverySettlement(): ItemReader<Company> {
        return JpaPagingItemReaderBuilder<Company>()
            .name("readChunkedDeliverySettlement")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString("select * from company")
            .build()
    }

    @Bean
    fun processorSettlement(): ItemProcessor<Company, SettlementSummary> {
        return ItemProcessor { company ->
            val deliverySettlements = deliverySettlementRepository.findByCompanyId(company.id)
            val packagingSettlements = packagingSettlementRepository.findByCompanyId(company.id)
            val shippingSettlements = shippingSettlementRepository.findByCompanyId(company.id)

            val totalUnitPrice = deliverySettlements
                .zip(packagingSettlements)
                .zip(shippingSettlements)
                .sumOf { (deliveryAndPackagingSettlement, shippingSettlement) ->
                    val (deliverySettlement, packagingSettlement) = deliveryAndPackagingSettlement
                    (deliverySettlement.unitPrice + packagingSettlement.unitPrice + shippingSettlement.unitPrice).toLong()
                }
            SettlementSummary(company, YearMonth.now(), totalUnitPrice)
        }
    }

    @Bean
    fun writeSettlementSummary(): JpaItemWriter<SettlementSummary> {
        val writer = JpaItemWriter<SettlementSummary>()
        writer.setEntityManagerFactory(entityManagerFactory)
        writer.setUsePersist(true)
        return writer
    }
}
