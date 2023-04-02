package com.anika.message.broker.consumer.processor;

public interface CrawlerTaskProcessor<T>{
    void process(T message);
}
