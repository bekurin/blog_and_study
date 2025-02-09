package core.rabbitmq.topic01.consumer;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
    public void receiverMessage(String message) {
        System.out.println(" [#] Received: " + message);
    }
}
