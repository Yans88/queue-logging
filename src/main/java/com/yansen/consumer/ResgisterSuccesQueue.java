package com.yansen.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class ResgisterSuccesQueue {

    @RabbitListener(queues = {"${queue.name}"})
    public void consumeMessageFromQueue(@Payload String message) {
        System.out.println("Message recieved from queue : " + message);
    }
}
