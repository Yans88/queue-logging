package com.yansen.components;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {

    @Autowired
    Queue queue;
    @Autowired
    private RabbitTemplate template;

    public void send(String payload) {
        template.convertAndSend(this.queue.getName(), payload);
    }
}
