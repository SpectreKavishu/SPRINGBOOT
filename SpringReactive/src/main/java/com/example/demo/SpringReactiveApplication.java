package com.example.demo;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

@SpringBootApplication
public class SpringReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
	}
	
	@Bean
    ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate() {
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(Collections.singletonMap(
            "bootstrap.servers", "creppfp2b32l8feglrkg.any.eu-central-1.mpx.prd.cloud.redpanda.com:9093"
        )));
    }

    @Bean
    ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate() {
        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.<String, String>create()
            .consumerProperty("bootstrap.servers", "creppfp2b32l8feglrkg.any.eu-central-1.mpx.prd.cloud.redpanda.com:9093")
            .subscription(Collections.singletonList("hello-world"));
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }

}
