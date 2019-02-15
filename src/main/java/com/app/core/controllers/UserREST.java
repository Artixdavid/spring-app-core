package com.app.core.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.core.models.entity.User;
import com.app.core.models.services.UserServiceImpl;

@CrossOrigin(origins = { "http://localhost:4200" }, methods = { RequestMethod.DELETE, RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT })
@RestController
@RequestMapping("/api")
public class UserREST {

	@Autowired
	private UserServiceImpl userService;

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.findById(id);
	}
	
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody User user) {
		return userService.save(user);
	}
	
	
}
