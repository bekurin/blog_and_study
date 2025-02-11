package core.rabbitmq.topic3pubsub.consumer;

import core.rabbitmq.topic3pubsub.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationSubscriber {
    public static final String CLIENT_URL = "/topic/notifications";
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationSubscriber(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void subscriber(String message) {
        System.out.println("Received Notification: " + message);
        simpMessagingTemplate.convertAndSend(CLIENT_URL, message);
    }
}
