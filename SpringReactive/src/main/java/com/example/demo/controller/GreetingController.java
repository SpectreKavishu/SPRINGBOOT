package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.User;

import com.example.demo.examples.WebClientExample;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class GreetingController {

	@GetMapping("/hello/{name}")
	public Mono<String> hello(@PathVariable String name) {
		return Mono.just("Hello, " + name + "!");
	}
	
	@GetMapping("/")
	public Mono<String> hello() {
		return Mono.just("Hello!");
	}

	@GetMapping("/users")
	public Flux<String> getUsersFromApi() {
		WebClientExample obj = new WebClientExample();
		return obj.getAllUsers();
	}

	@GetMapping("/{id}")
	public Mono<String> getUsersFromApi(@PathVariable String id) {
		WebClientExample obj = new WebClientExample();
		return obj.getUser(id);
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public String userEndpoint() {
		return "Hello, User!";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminEndpoint() {
		return "Hello, Admin!";
	}
}
