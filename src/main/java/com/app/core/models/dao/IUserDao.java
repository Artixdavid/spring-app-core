package com.app.core.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.app.core.models.entity.User;

public interface IUserDao extends CrudRepository<User, Long>{
	
	

}
