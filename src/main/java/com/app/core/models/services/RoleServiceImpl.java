package com.app.core.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.core.models.dao.IRoleDao;
import com.app.core.models.entity.Role;

@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private IRoleDao roleDao;

	@Override
	public List<Role> findAll() {
		return (List<Role>) this.roleDao.findAll();
	}

	@Override
	public Role findById(Long id) {
		return (Role) this.roleDao.findById(id).orElse(null);
	}

	@Override
	public Role save(Role user) {
		return (Role) this.roleDao.save(user);
	}

	@Override
	public void delete(Long id) {
		this.roleDao.deleteById(id);
	}

}
