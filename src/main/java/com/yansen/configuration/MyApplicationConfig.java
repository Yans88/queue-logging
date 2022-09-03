package com.yansen.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyApplicationConfig {

    @Value("${queue.name}")
    private String message;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Queue queue() {
        return new Queue(message, true);
    }
}
