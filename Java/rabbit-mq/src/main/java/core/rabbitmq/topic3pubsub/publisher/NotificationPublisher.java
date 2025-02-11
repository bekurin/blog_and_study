package core.rabbitmq.topic3pubsub.publisher;

import core.rabbitmq.topic3pubsub.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public NotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String message) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.FANOUT_EXCHANGE, "", message); // Fanout에서 routing key는 무시됨
        System.out.println("[#] Published Notification: " + message);
    }
}
