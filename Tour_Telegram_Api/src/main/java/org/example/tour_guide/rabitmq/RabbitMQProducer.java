package org.example.tour_guide.rabitmq;

import lombok.RequiredArgsConstructor;
import org.example.tour_guide.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQProducer {

//    private final RabbitTemplate rabbitTemplate;
//
//    private final RabbitMQConfig rabbitMqConfig;
//
//    public void sendMessage(String message) {
//        rabbitTemplate.convertAndSend(
//                rabbitMqConfig.getExchange(),
//                rabbitMqConfig.getRoutingKey(),
//                message);
//    }

}