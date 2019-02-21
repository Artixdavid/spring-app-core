package com.app.core.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.app.core.models.entity.Role;

public interface IRoleDao extends CrudRepository<Role, Long>{

}
