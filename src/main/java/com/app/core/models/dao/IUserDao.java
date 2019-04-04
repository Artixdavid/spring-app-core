package com.app.core.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.app.core.models.entity.User;

public interface IUserDao extends CrudRepository<User, Long>{
	
	public User findByUsername(String username);
	
	@Query("select u from User u where u.email =?1")
	public User findByEmail(String email);
	
	
	
}
