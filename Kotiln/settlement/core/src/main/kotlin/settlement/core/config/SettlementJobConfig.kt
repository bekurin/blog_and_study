package settlement.core.config

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
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import settlement.core.domain.company.Company
import settlement.core.domain.settlement.repository.DeliverySettlementRepository
import settlement.core.domain.settlement.repository.PackagingSettlementRepository
import settlement.core.domain.settlement.repository.ShippingSettlementRepository
import settlement.core.domain.snapshot.SettlementSummary
import java.time.YearMonth

@Configuration
class SettlementJobConfig(
    private val transactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val deliverySettlementRepository: DeliverySettlementRepository,
    private val packagingSettlementRepository: PackagingSettlementRepository,
    private val shippingSettlementRepository: ShippingSettlementRepository,
) : DefaultBatchConfiguration() {

    private val chunkSize = 10

    @Bean
    fun settlementJob(): Job {
        return JobBuilder("settlementJob", jobRepository())
            .preventRestart()
            .start(companyStep())
            .incrementer(DailyJobTimeStamper())
            .build()
    }

    @Bean
    @JobScope
    fun companyStep(): Step {
        return StepBuilder("companyStep", jobRepository())
            .chunk<Company, SettlementSummary>(chunkSize, transactionManager)
            .reader(readChunkedDeliverySettlement())
            .processor(processorSettlement(null))
            .writer(writeSettlementSummary())
            .build()
    }

    @Bean
    fun readChunkedDeliverySettlement(): ItemReader<Company> {
        return JpaPagingItemReaderBuilder<Company>()
            .name("readChunkedDeliverySettlement")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString("SELECT c FROM Company c")
            .build()
    }

    /**
     * jobParameters를 사용하려면 StepScope가 정의되어 있어야한다.
     */
    @Bean
    @StepScope
    fun processorSettlement(
        @Value("#{jobParameters[settlementYearMonth]}") settlementYearMonth: String?
    ): ItemProcessor<Company, SettlementSummary> {
        val yearMonth = YearMonth.parse(settlementYearMonth)
        val (settlementStartDate, settlementEndDate) = yearMonth.atDay(1) to yearMonth.atEndOfMonth()
        return ItemProcessor { company ->
            val deliverySettlements = deliverySettlementRepository.findByCompanyIdAndDeliveryDateBetween(
                company.id,
                settlementStartDate,
                settlementEndDate
            )
            val packagingSettlements = packagingSettlementRepository.findByCompanyIdAndPackagingDateBetween(
                company.id,
                settlementStartDate,
                settlementEndDate
            )
            val shippingSettlements = shippingSettlementRepository.findByCompanyIdAndShippingDateBetween(
                company.id,
                settlementStartDate,
                settlementEndDate
            )
            val totalUnitPrice = deliverySettlements
                .zip(packagingSettlements)
                .zip(shippingSettlements)
                .sumOf { (deliveryAndPackagingSettlement, shippingSettlement) ->
                    val (deliverySettlement, packagingSettlement) = deliveryAndPackagingSettlement
                    (deliverySettlement.unitPrice + packagingSettlement.unitPrice + shippingSettlement.unitPrice).toLong()
                }
            if (totalUnitPrice == 0L) {
                null
            } else {
                SettlementSummary(company, yearMonth, totalUnitPrice)
            }
        }
    }

    @Bean
    fun writeSettlementSummary(): JpaItemWriter<SettlementSummary> {
        return JpaItemWriter<SettlementSummary>()
            .apply {
                setEntityManagerFactory(entityManagerFactory)
                setUsePersist(false)
            }
    }
}
