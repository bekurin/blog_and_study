package core.rabbitmq.topic3pubsub.controller;

public class NotificationMessage {
    private String message;

    public NotificationMessage() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContent() {
        return message;
    }
}
