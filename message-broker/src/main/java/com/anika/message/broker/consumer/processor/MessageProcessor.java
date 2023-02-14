package com.anika.message.broker.consumer.processor;

public interface MessageProcessor<T> {
    void process(T message);
}
