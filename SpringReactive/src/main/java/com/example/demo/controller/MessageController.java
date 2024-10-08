package com.example.demo.controller;

import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/messages")
class MessageController {
    private final ReactiveKafkaProducerTemplate<String, String> kafkaProducer;
    private final ReactiveKafkaConsumerTemplate<String, String> kafkaConsumer;

    public MessageController(ReactiveKafkaProducerTemplate<String, String> kafkaProducer,
                             ReactiveKafkaConsumerTemplate<String, String> kafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaConsumer = kafkaConsumer;
    }

    @PostMapping
    public Mono<String> sendMessage(@RequestBody String message) {
        return kafkaProducer.send("hello-world", message)
            .then(Mono.just("Mono Message sent successfully"));
    }

    @GetMapping
    public Flux<String> receiveMessages() {
        return kafkaConsumer.receiveAutoAck()
            .map(record -> record.value());
    }
}

