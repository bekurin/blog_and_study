package batch.core.v4

import batch.core.v4.Constant.DataSourceA
import batch.core.v4.Constant.DataSourceB
import batch.core.v4.a.entity.SettlementA
import batch.core.v4.b.entity.SettlementB
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
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class MigrationJobConfig(
    @Qualifier(DataSourceA.TRANSACTION_MANGER_REF)
    private val transactionManagerA: PlatformTransactionManager,
    @Qualifier(DataSourceB.TRANSACTION_MANGER_REF)
    private val transactionManagerB: PlatformTransactionManager,
    @Qualifier(DataSourceA.ENTITY_MANAGER_FACTORY_REF)
    private val entityManagerFactoryA: EntityManagerFactory,
    @Qualifier(DataSourceB.ENTITY_MANAGER_FACTORY_REF)
    private val entityManagerFactoryB: EntityManagerFactory
) : DefaultBatchConfiguration() {
    private val chunkSize = 500

    @Bean
    @JobScope
    fun migrationJob(): Job {
        return JobBuilder("migrationJob", jobRepository())
            .start(migrateSettlementAToSettlementB())
            .build()
    }

    @Bean
    @StepScope
    fun migrateSettlementAToSettlementB(): Step {
        return StepBuilder("migrateSettlementAToSettlementB", jobRepository())
            .chunk<SettlementA, SettlementB>(chunkSize, transactionManagerB)
            .reader(readChunkSettlementA())
            .processor(createSettlementBUsingSettlementA())
            .writer(insertChunkSettlementB())
            .build()
    }

    @Bean
    fun readChunkSettlementA(): ItemReader<SettlementA> {
        return JpaPagingItemReaderBuilder<SettlementA>()
            .name("reader")
            .entityManagerFactory(entityManagerFactoryA)
            .pageSize(chunkSize)
            .queryString("select * from settlement_a")
            .build()
    }

    @Bean
    fun createSettlementBUsingSettlementA(): ItemProcessor<SettlementA, SettlementB> {
        return ItemProcessor { settlementA ->
            SettlementB(settlementA.unitPrice, settlementA.paidType, true)
        }
    }

    @Bean
    fun insertChunkSettlementB(): JpaItemWriter<SettlementB> {
        val writer = JpaItemWriter<SettlementB>()
        writer.setEntityManagerFactory(entityManagerFactoryB)
        // true -> insert 만 실행, false -> upsert 실행
        writer.setUsePersist(true)
        return writer
    }
}
