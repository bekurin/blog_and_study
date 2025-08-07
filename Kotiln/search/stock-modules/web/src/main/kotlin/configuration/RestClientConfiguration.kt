package configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.support.HttpRequestWrapper
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.util.UriComponentsBuilder
import stock.modules.web.service.StockPriceApiClient
import java.net.URI

@Configuration
@EnableConfigurationProperties(ApiProperty::class)
class RestClientConfiguration(
    private val apiProperty: ApiProperty
) {
    @Bean
    fun stockPriceRestClient(): StockPriceApiClient {
        val restClient = RestClient.builder()
            .baseUrl(apiProperty.baseUrl)
            .requestInterceptor { request, body, execution ->
                val newUri = UriComponentsBuilder.fromUri(request.uri)
                    .queryParam("serviceKey", apiProperty.serviceKey)
                    .build(true)
                    .toUri()
                val newRequest = object : HttpRequestWrapper(request) {
                    override fun getURI(): URI {
                        return newUri
                    }
                }
                execution.execute(newRequest, body)
            }
            .build()

        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build()
        return httpServiceProxyFactory.createClient(StockPriceApiClient::class.java)
    }
}