package com.example.demo.examples;

import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientExample {

	public Mono<User> getUserObject(String userId) {
		WebClient client = WebClient.create("http://localhost:8083");
		return client.get().uri("/users/{id}", userId).retrieve().bodyToMono(User.class).map(item -> new User());
	}
	
	public Mono<String> getUser(String userId) {
		WebClient client = WebClient.create("http://localhost:8083");
		return client.get().uri("/users/{id}", userId).retrieve().bodyToMono(String.class);
	}

	public Flux<String> getAllUsers() {
		WebClient client = WebClient.create("http://localhost:8083");
		return client.get().uri("/users").retrieve().bodyToFlux(String.class);
	}
}
