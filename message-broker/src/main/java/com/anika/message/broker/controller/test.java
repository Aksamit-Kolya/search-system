package com.anika.message.broker.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/v2")
public class test {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/test")
    public String findSongs() {
        return rabbitTemplate.toString();
    }

}
