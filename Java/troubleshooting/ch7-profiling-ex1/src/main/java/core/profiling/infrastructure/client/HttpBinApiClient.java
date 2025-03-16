package core.profiling.infrastructure.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface HttpBinApiClient {
    @PostExchange("/delay/{delayedSeconds}")
    void delay(@PathVariable int delayedSeconds);
}
