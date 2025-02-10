package core.rabbitmq.topic02workqueue.controller;

import core.rabbitmq.topic02workqueue.producer.WorkQueueProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkQueueController {
    private final WorkQueueProducer workQueueProducer;

    public WorkQueueController(WorkQueueProducer workQueueProducer) {
        this.workQueueProducer = workQueueProducer;
    }

    @PostMapping("work-queue")
    public String workQueue(
            @RequestParam String message,
            @RequestParam int duration
    ) {
        workQueueProducer.sendWorkQueue(message, duration);
        return "Work queue sent successfully (message=" + message + ", duration=" + duration + ")";
    }
}
