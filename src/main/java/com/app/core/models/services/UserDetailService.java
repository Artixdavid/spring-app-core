package com.app.core.models.services;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.core.models.dao.IUserDao;
import com.app.core.models.entity.Role;

@Service("jpaUserDetailService")
public class UserDetailService implements UserDetailsService {

	@Autowired
	private IUserDao userDAO;

	private Logger logger = LoggerFactory.getLogger(UserDetailService.class);

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.app.core.models.entity.User user = userDAO.findByUsername(username);

		System.out.println("Usuerio: " + user);
		if (username == null) {
			logger.error("Usuario '" + username + "' no existe");
			throw new UsernameNotFoundException("Usuario no existe");
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		if (authorities.isEmpty()) {
			logger.error("Usuario '" + username + "' no tiene roles asociados");
			throw new UsernameNotFoundException("Usuario sin roles");
		}

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

}
