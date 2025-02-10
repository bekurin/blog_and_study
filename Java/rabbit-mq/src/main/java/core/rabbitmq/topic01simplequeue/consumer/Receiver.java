package core.rabbitmq.topic01simplequeue.consumer;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
    public void receiverMessage(String message) {
        System.out.println(" [#] Received: " + message);
    }
}
