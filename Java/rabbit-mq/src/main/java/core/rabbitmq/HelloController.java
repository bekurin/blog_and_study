package core.rabbitmq;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public HashMap<String, String> hello(@RequestParam String name) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("key", "Hello World: " + name);
        return hashMap;
    }
}
