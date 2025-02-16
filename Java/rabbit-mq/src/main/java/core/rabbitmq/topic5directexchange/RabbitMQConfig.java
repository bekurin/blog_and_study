package core.rabbitmq.topic5directexchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ERROR_QUEUE = "error_queue";
    public static final String WARN_QUEUE = "warn_queue";
    public static final String INFO_QUEUE = "info_queue";

    public static final String DIRECT_EXCHANGE = "direct_exchange";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Queue errorQueue() {
        return new Queue(ERROR_QUEUE, false);
    }

    @Bean
    public Queue warnQueue() {
        return new Queue(WARN_QUEUE, false);
    }

    @Bean
    public Queue infoQueue() {
        return new Queue(INFO_QUEUE, false);
    }


    @Bean
    public Binding errorBinding() {
        return BindingBuilder.bind(errorQueue()).to(directExchange()).with("error");
    }

    @Bean
    public Binding warnBinding() {
        return BindingBuilder.bind(warnQueue()).to(directExchange()).with("warn");
    }

    @Bean
    public Binding infoBinding() {
        return BindingBuilder.bind(infoQueue()).to(directExchange()).with("info");
    }
}
