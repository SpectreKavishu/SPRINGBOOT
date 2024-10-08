package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @Configuration, @EnableAutoConfiguration, @ComponentScan -> @SpringBootApplication 
// This is a convenience annotation that is equivalent to declaring @SpringBootConfiguration, 
// @EnableAutoConfiguration and @ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo")
@EnableJpaRepositories(basePackages = "com.example.demo.repository")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
