package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final WebClient.Builder webClientBuilder;

	public OrderController(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}

	@GetMapping("/{orderId}")
	public Mono<OrderDetails> getOrderDetails(@PathVariable String orderId) {
		return getOrder(orderId)
				.flatMap(order -> getUser(order.getUserId()).map(user -> new OrderDetails(order, user)));
	}

	private Mono<Order> getOrder(String orderId) {
		return webClientBuilder.build().get().uri("http://order-service/orders/{orderId}", orderId).retrieve()
				.bodyToMono(Order.class);
	}

	private Mono<User> getUser(String userId) {
		return webClientBuilder.build().get().uri("http://user-service/users/{userId}", userId).retrieve()
				.bodyToMono(User.class);
	}
}

@Getter
@Setter
@AllArgsConstructor
class Order {
	private String orderId;
	private String userId;
}

@Getter
@Setter
@AllArgsConstructor
class User {
	private String userId;
	private String name;
}

@Getter
@Setter
@AllArgsConstructor
class OrderDetails {
	private Order order;
	private User user;
}
