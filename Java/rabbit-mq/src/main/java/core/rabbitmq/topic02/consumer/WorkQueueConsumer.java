package core.rabbitmq.topic02.consumer;

import org.springframework.stereotype.Component;

@Component
public class WorkQueueConsumer {
    public void workQueueTask(String message) {
        String[] messageParts = message.split("\\|");
        String originMessage = messageParts[0];
        int duration = Integer.parseInt(messageParts[1]);

        String logMessage = originMessage + " (duration: " + duration + "ms)";
        System.out.println("# Received: " + logMessage);

        try {
            System.out.println("now...sleep time " + duration + "ms");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("# Completed: " + logMessage);
    }
}
