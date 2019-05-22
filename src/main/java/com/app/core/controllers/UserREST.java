package com.app.core.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.app.core.auth.service.JWTService;
import com.app.core.constants.StatusConstans;
import com.app.core.constants.TokenConstans;
import com.app.core.dto.ActualizarUsuarioDto;
import com.app.core.dto.CrearUsuarioDto;
import com.app.core.models.dao.IUserDao;
import com.app.core.models.entity.Status;
import com.app.core.models.entity.User;
import com.app.core.models.services.IEmailService;
import com.app.core.models.services.IStatusService;
import com.app.core.models.services.IUserService;
import com.app.core.models.services.UserDetailService;
import com.app.core.models.services.impl.MailClient;

//@CrossOrigin(origins = { "http://localhost:4200" }, methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@RestController
@RequestMapping("/api")
public class UserREST {

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IStatusService statusService;

	@Autowired
	private IEmailService emailService;

	@Autowired
	private MailClient mailClient;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private UserDetailService userDetailService;

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.findAll();
	}

	@GetMapping("/users/{username}/user")
	public User getUserByUsername(@PathVariable String username) {

		User user = this.userService.findByUserName(username);

		return user;
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
	public User create(@RequestBody CrearUsuarioDto createUser) {

		User user = new User();

		user.setUsername(createUser.getEmail());
		user.setFirstName(createUser.getFirstName());
		user.setLastName(createUser.getLastName());
		user.setEmail(createUser.getEmail());

		BCryptPasswordEncoder bCryptPassEncoder = new BCryptPasswordEncoder();
		String passwordEncode = bCryptPassEncoder.encode(createUser.getPassword());
		user.setPassword(passwordEncode);
		// user.setPhone(createUser.getPhone());

		// Status status = new Status();

		System.out.println("Status---------------------------------------->: " + StatusConstans.ACTIVO);
		Status status = statusService.findById(StatusConstans.ACTIVO);

		emailService.sendEmail(user);

		user.setStatus(status);
		user.setEnabled(true);
		user = userService.save(user);

		return userService.save(user);
	}

	@PutMapping("/users/{userName}")
	@ResponseStatus(HttpStatus.CREATED)
	public User update(@RequestBody ActualizarUsuarioDto user, @PathVariable String userName) {
		User usuario = userService.findByUserName(userName);
		if (usuario == null) {
			System.out.println("Usuario no encontrado");
		}

		usuario.setFirstName(user.getFirstName());
		usuario.setLastName(user.getLastName());
		usuario.setAddress(user.getAddress());
		usuario.setFechaNacimiento(user.getFechaNacimiento());
		usuario.setAboutMe(user.getAboutMe());
		usuario.setSecondName(user.getSecondName());
		
		userService.save(usuario);

		return usuario;
	}

	@DeleteMapping("/users/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}

	@GetMapping("/users/{email}/recovery")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void recovery(@PathVariable String email) throws IOException {

		System.out.println("Email recibido: " + email);
		User user = userDao.findByEmail(email);

		if (user != null) {

			System.out.println("Se enviara un email a " + user.getEmail());

			UserDetails userDetails = this.userDetailService.loadUserByUsername(user.getUsername());
			Collection<? extends GrantedAuthority> role = userDetails.getAuthorities();

			String token = this.jwtService.createJWT(user.getUsername(), this.jwtService.setClaims(role));

			System.out.println("Aqui el token: " + token);

			mailClient.prepareAndSend(user, token);

		}
	}

	@GetMapping("/users/{token}/validate")
	public User validate(@PathVariable String token) {

		String username = this.jwtService.getUsername(TokenConstans.PREFIX_TOKEN + token);
		if (username != null) {
			return userDao.findByUsername(username);
		}

		return null;
	}

}
