package core.profiling.controller;

import core.profiling.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoController {
    private final DemoService demoService;

    @GetMapping("/demo")
    public void demo() {
        demoService.delay(5);
    }
}
