package org.example.tour_guide.rabitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("application.properties")
@Slf4j
public class RabbitMQConsumer {
//    @RabbitListener(queues = "${sample.rabbitmq.queue}")
//    public void receiveMessage(String message) {
//        log.info("Received message: " + message);
//    }

}