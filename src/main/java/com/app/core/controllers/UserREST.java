package com.app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

		return userService.findall();

	}
}
