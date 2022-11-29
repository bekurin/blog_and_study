package core.r2dbch2.config

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.h2.H2ConnectionOption
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
@EnableR2dbcRepositories
class R2dbcConfig : AbstractR2dbcConfiguration() {

    @Value("\${spring.r2dbc.username}")
    lateinit var username: String

    @Value("\${spring.r2dbc.url}")
    lateinit var url: String

    override fun connectionFactory(): ConnectionFactory {
        return H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                .url(url)
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                .username(username)
                .build()
        )
    }

    @Bean
    fun h2DbInitializer(): ConnectionFactoryInitializer {
        return ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory())
            setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
        }
    }
}