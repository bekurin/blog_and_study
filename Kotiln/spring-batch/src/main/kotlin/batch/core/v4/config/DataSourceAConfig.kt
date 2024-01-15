package batch.core.v4.config

import batch.core.v4.Constant.DataSourceA
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = DataSourceA.ENTITY_MANAGER_FACTORY_REF,
    basePackages = [DataSourceA.BASE_PACKAGE],
    transactionManagerRef = DataSourceA.TRANSACTION_MANGER_REF
)
class DataSourceAConfig {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.datasource-a")
    fun dataSourceA(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Primary
    @Bean
    fun entityManagerFactoryA(
        dataSourceA: DataSource,
        builder: EntityManagerFactoryBuilder,
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSourceA)
            .packages(DataSourceA.BASE_PACKAGE)
            .persistenceUnit(DataSourceA.ENTITY_MANAGER)
            .build()
    }

    @Primary
    @Bean
    fun transactionManagerA(
        entityManagerFactoryA: LocalContainerEntityManagerFactoryBean,
    ): PlatformTransactionManager {
        return JpaTransactionManager(
            entityManagerFactoryA.`object` ?: throw NullPointerException("EntityManager can not be null"),
        )
    }
}
