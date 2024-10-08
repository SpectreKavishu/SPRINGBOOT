package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.demo.controller.GreetingController;

@WebFluxTest(GreetingController.class)
public class GreetingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testHelloEndpoint() {
        webTestClient.get().uri("/hello/World")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello, World!");
    }
}
