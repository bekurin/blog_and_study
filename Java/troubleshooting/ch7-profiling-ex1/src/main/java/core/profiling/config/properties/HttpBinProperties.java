package core.profiling.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("http-bin")
public class HttpBinProperties {
    private String baseUrl;
}
