package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

// @RestController is combination of @Controller and @ResponseBody
// If you want to render view, @Controller is recommended, see UserViewController

@RestController
@RequestMapping("/api")
public class DemoController {

	@GetMapping("/greet")
	// @RequestMapping(value = "/greet", method = RequestMethod.GET)
	@Operation(summary = "Get a greeting message", description = "Returns default message")
	public String greet(@RequestParam(defaultValue = "World") String name) {
		return "Hello, " + name + "!";
	}

	@GetMapping("/greet/{name}")
	@Operation(summary = "Get a greeting message", description = "Returns a personalized greeting based on the name provided")
	public String greetWithPathVariable(@PathVariable String name) {
		return "Hello, " + name + "!";
	}
	
}
