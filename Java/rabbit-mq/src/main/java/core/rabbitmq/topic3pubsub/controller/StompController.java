package core.rabbitmq.topic3pubsub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public StompController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/send")
    public void sendMessage(String jsonMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            NotificationMessage message = objectMapper.readValue(jsonMessage, NotificationMessage.class);
            System.out.println("Received message: " + message.getContent());
            simpMessagingTemplate.convertAndSend("/topic/notification", message);
        } catch (JsonProcessingException e) {
            System.err.println("Invalid JSON format: " + jsonMessage);
        }
    }
}
