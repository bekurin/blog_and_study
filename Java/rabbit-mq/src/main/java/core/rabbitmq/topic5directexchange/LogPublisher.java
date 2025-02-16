package core.rabbitmq.topic5directexchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogPublisher {

    private final RabbitTemplate rabbitTemplate;

    public LogPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String routingKey, String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, routingKey, message);
        System.out.println("message published :" + routingKey + ":" + message);
    }
}
