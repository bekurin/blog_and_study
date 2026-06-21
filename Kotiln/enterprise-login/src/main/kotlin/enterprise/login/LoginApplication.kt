package enterprise.login

import enterprise.login.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class LoginApplication

fun main(args: Array<String>) {
    runApplication<LoginApplication>(*args)
}
