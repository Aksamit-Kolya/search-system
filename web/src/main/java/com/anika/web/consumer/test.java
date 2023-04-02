package com.anika.web.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
//@EnableRabbit
//public class test {
//    @RabbitListener(queues = "crawler.process")
//    public void processMyQueue(Object message) {
//        System.out.printf("Received from myQueue : " + message);
//    }
//}
