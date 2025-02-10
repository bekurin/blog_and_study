package core.rabbitmq.topic02.consumer;

import org.springframework.stereotype.Component;

@Component
public class WorkQueueConsumer {
    public void workQueueTask(String message) {
        String[] messageParts = message.split("\\|");
        String originMessage = messageParts[0];
        int duration = Integer.parseInt(messageParts[1].trim());

        String logMessage = originMessage + " (duration: " + duration + "ms)";
        System.out.println("# Received: " + logMessage);

        try {
            int seconds = duration / 1000;

            for (int i = 0; i < seconds; i++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n[#] Completed: " + logMessage);
    }
}
