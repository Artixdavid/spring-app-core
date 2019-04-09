package com.app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.core.models.entity.Role;
import com.app.core.models.services.IRoleService;

@RestController
@RequestMapping("/api")
public class RoleREST {

	@Autowired
	private IRoleService roleService;

	@PostMapping("/role")
	public Role create(@RequestBody Role role) {
		if (role != null) {
			return this.roleService.save(role);
		}
		return null;
	}

	@DeleteMapping("/role/{id}")
	private void delete(@PathVariable Long id) {
		if (id != null) {
			this.roleService.delete(id);
		}
	}

}
