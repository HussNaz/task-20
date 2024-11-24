package com.example.kafkademo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        System.out.printf("Consumed message: %s%n", record.value());
    }
}
