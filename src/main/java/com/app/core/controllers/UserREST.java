package com.app.core.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.app.core.match.MatchCreateUser;
import com.app.core.models.entity.User;
import com.app.core.models.services.RoleServiceImpl;
import com.app.core.models.services.UserServiceImpl;

@CrossOrigin(origins = { "http://localhost:4200" }, methods = { RequestMethod.DELETE, RequestMethod.GET,
		RequestMethod.POST, RequestMethod.PUT })
@RestController
@RequestMapping("/api")
public class UserREST {

	@Autowired
	private UserServiceImpl userService;
	
	private RoleServiceImpl roleService;

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.findAll();
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		// return userService.findById(id);
		User user = null;
		Map<String, Object> response = new HashMap<>();
		try {
			user = userService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (user == null) {
			response.put("mensaje", "Usuario no encontrado!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody MatchCreateUser createUser) {
		
		User user = new User();
		
		user.setUsername(createUser.getUsername());
		user.setFirstName(createUser.getFirstName());
		user.setLastName(createUser.getLastName());
		user.setEmail(createUser.getEmail());
		
		BCryptPasswordEncoder bCryptPassEncoder = new BCryptPasswordEncoder();
		String passwordEncode = bCryptPassEncoder.encode(createUser.getPassword());
		user.setPassword(passwordEncode);
		user.setPhone(createUser.getPhone());
		
		
		//user.setPhoto_ex(createUser.getP)());
		user = userService.save(user);
		
		
		
		
		
		
		
		return userService.save(user);
	}

	@PutMapping("/users/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public User update(@RequestBody User user, @PathVariable Long id) {
		User usuario = userService.findById(id);
		if (usuario == null) {
			System.out.println("Usuario no encontrado");
		}

		usuario.setFirstName(user.getFirstName());
		usuario.setLastName(user.getLastName());
		userService.save(usuario);

		return user;
	}

	@DeleteMapping("/users/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}

}
