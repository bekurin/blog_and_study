package core.rabbitmq.topic5directexchange;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final GlobalExceptionHandler globalExceptionHandler;

    public LogController(GlobalExceptionHandler globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @GetMapping("/error")
    public ResponseEntity<String> errorAPI() {
        try {
            String value = null;
            value.getBytes(); // null pointer
        } catch (Exception e) {
            globalExceptionHandler.handleException(e);
        }
        return ResponseEntity.ok("Controller Nullpointer Exception 처리 ");
    }


    @GetMapping("/warn")
    public ResponseEntity<String> warnAPI() {
        try {
            throw new IllegalArgumentException("invalid argument입니다.");
        } catch (Exception e) {
            globalExceptionHandler.handleException(e);
        }
        return ResponseEntity.ok("Controller IllegalArugument Exception 처리 ");
    }

    @PostMapping("/info")
    public ResponseEntity<String> infoAPI(@RequestBody String message) {
        globalExceptionHandler.handleMessage(message);
        return ResponseEntity.ok("Controller Info log 발송 처리 ");
    }

}
