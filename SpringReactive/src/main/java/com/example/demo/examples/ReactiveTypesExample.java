package com.example.demo.examples;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public class ReactiveTypesExample {

    public static Mono<String> getUser(String userId) {
        return Mono.just("User: " + userId);
    }

	public static Flux<Integer> getNumbers() {
        return Flux.just(1, 2, 3, 4, 5);
    }

    public static void main(String[] args) {
        // Using Mono
        getUser("123")
            .subscribe(user -> System.out.println(user));

        // Using Flux
        getNumbers()
            .map(n -> n * 2)
            .filter(n -> n > 5)
            .subscribe(n -> System.out.println(n));
        
    }
}