package core.rabbitmq.topic6dlxdlq;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @GetMapping
    public ResponseEntity<String> sendOrderMessage(
            @RequestParam String message
    ) {
        return ResponseEntity.ok("Order Completed Message Send: " + message);
    }
}
