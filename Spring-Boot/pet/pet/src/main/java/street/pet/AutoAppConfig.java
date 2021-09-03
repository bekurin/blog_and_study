package street.pet;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ComponentScan
@Configuration
@EnableJpaAuditing
public class AutoAppConfig {
}
