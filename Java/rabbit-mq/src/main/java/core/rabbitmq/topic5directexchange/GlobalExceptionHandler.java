package core.rabbitmq.topic5directexchange;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final LogPublisher logPublisher;

    public GlobalExceptionHandler(LogPublisher logPublisher) {
        this.logPublisher = logPublisher;
    }

    public void handleException(Exception e) {
        String message = e.getMessage();

        String routingKey;

        if (e instanceof NullPointerException) {
            routingKey = "error";
        } else if (e instanceof IllegalArgumentException) {
            routingKey = "warn";
        } else {
            routingKey = "error";
        }

        logPublisher.publish(routingKey, "Exception이 발생했음 : " + message);
    }

    public void handleMessage(String message) {
        String routingKey = "info";
        logPublisher.publish(routingKey, "Info Log : " + message);
    }
}
