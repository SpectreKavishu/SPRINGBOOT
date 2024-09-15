package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StreamConsumerService {

	@KafkaListener(topics = "hello-world", groupId = "default")
	public void consumeMessage(String message) {
		System.out.println("Received message from Redpanda: " + message);
		// Add any additional processing logic here
	}
}
