package com.app.core.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.app.core.models.entity.Status;
import com.app.core.models.services.impl.StatusServiceImpl;

@CrossOrigin(origins = { "http://localhost:4200" }, methods = { RequestMethod.GET,  })
@RestController
@RequestMapping("/api")
public class StatusREST {

	@Autowired
	private StatusServiceImpl statusService;

	@GetMapping("/status")
	public List<Status> getUsers() {
		return statusService.findAll();
	}
	

}
