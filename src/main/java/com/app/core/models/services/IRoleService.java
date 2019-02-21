package com.app.core.models.services;

import java.util.List;

import com.app.core.models.entity.Role;

public interface IRoleService {
	
	public List<Role> findAll();
	
	public Role findById(Long id);
	
	public Role save(Role user);
	
	public void delete(Long id);
	
}
