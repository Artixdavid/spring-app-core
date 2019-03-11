package com.app.core.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.models.dao.IUserDao;
import com.app.core.models.entity.User;
import com.app.core.models.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return (List<User>) userDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return (User) userDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public User save(User user) {
		return userDao.save(user);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		userDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByUserName(String username) {
		return userDao.findByUsername(username);
	}
}
