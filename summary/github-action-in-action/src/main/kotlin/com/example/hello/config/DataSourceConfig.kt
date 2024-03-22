package com.example.hello.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    fun datasource(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }
}
