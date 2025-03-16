package core.profiling.config;

import core.profiling.config.properties.HttpBinProperties;
import core.profiling.infrastructure.client.HttpBinApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class RestApiClientConfig {
    private final HttpBinProperties httpBinProperties;

    @Bean
    public HttpBinApiClient httpBinApiClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(httpBinProperties.getBaseUrl())
                .build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder()
                .exchangeAdapter(RestClientAdapter.create(restClient))
                .build();
        return httpServiceProxyFactory.createClient(HttpBinApiClient.class);
    }
}
