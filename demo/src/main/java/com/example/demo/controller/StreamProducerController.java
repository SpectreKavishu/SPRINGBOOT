package com.example.demo.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/stream")
public class StreamProducerController {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public StreamProducerController(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@PostMapping("/publish")
	public ResponseEntity<String> publishMessage() {
		kafkaTemplate.send("hello-world", "kavishu");
		return ResponseEntity.ok("Message sent to Redpanda");
	}
}
