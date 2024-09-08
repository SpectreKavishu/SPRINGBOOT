package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
}
