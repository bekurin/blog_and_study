package core.profiling.service;

import core.profiling.infrastructure.client.HttpBinApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoService {
    private final HttpBinApiClient httpBinApiClient;

    public void delay(int delaySeconds) {
        httpBinApiClient.delay(delaySeconds);
    }
}
