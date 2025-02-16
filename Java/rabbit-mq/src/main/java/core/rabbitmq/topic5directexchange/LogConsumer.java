package core.rabbitmq.topic5directexchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LogConsumer {

    @RabbitListener(queues = RabbitMQConfig.ERROR_QUEUE)
    public void consumeError(String message) {
        System.out.println("[ERROR]를 받음 : " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.WARN_QUEUE)
    public void consumeWarn(String message) {
        System.out.println("[WARN]를 받음 : " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.INFO_QUEUE)
    public void consumeInfo(String message) {
        System.out.println("[INFO]를 받음 : " + message);
    }

}
