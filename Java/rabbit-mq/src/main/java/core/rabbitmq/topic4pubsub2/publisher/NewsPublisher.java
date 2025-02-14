package core.rabbitmq.topic4pubsub2.publisher;

import core.rabbitmq.topic4pubsub2.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NewsPublisher {
    private final RabbitTemplate rabbitTemplate;

    public NewsPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String publish(String news) {
        return publishMessage(news, " 관련 새 소식이 있어요.");
    }

    private String publishMessage(String news, String messageSuffix) {
        String message = news + messageSuffix;
        rabbitTemplate.convertAndSend(RabbitMqConfig.FANOUT_EXCHANGE_FOR_NEWS, news, message);
        System.out.println("[#] News published: " + message);
        return message;
    }
}
