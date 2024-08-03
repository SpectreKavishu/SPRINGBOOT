package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
public class UserViewController {

	private final UserService userService;

	public UserViewController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public String getUsers(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "userList";
	}
}
