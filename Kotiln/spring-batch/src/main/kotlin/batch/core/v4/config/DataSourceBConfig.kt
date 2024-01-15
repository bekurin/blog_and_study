package batch.core.v4.config

import batch.core.v4.Constant.DataSourceB
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = DataSourceB.ENTITY_MANAGER_FACTORY_REF,
    basePackages = [DataSourceB.BASE_PACKAGE],
    transactionManagerRef = DataSourceB.TRANSACTION_MANGER_REF
)
class DataSourceBConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.datasource-b")
    fun dataSourceB(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean
    fun entityManagerFactoryB(
        dataSourceB: DataSource,
        builder: EntityManagerFactoryBuilder,
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSourceB)
            .packages(DataSourceB.BASE_PACKAGE)
            .persistenceUnit(DataSourceB.ENTITY_MANAGER)
            .build()
    }

    @Bean
    fun transactionManagerB(
        entityManagerFactoryB: LocalContainerEntityManagerFactoryBean,
    ): PlatformTransactionManager {
        return JpaTransactionManager(
            entityManagerFactoryB.`object` ?: throw NullPointerException("EntityManager can not be null"),
        )
    }
}
