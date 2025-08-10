package configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "stock.api")
data class ApiProperty(
    val serviceKey: String,
    val baseUrl: String
)