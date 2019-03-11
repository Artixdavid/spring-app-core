package com.app.core.auth.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.app.core.auth.service.JWTService;
import com.app.core.constants.ContentTypeConstans;
import com.app.core.constants.TokenConstans;
import com.app.core.match.MatchUpdateUser;
import com.app.core.models.entity.User;
import com.app.core.models.services.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private JWTService jwtService;

	private UserServiceImpl userService;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService,
			UserServiceImpl userService) {
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = request.getParameter("username");
		String password = obtainPassword(request);

		if (username != null && password != null) {
			logger.info("Aqui los datos del user: " + username);
			logger.info("Aqui la pass del user: " + password);
		} else {
			User user = null;
			try {
				user = new ObjectMapper().readValue(request.getInputStream(), User.class);
				username = user.getUsername();
				password = user.getPassword();
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		response.setStatus(200);
		username = username.trim();
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = jwtService.create(authResult);

		response.addHeader(TokenConstans.HEADER_STRING, TokenConstans.PREFIX_TOKEN + token);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("token", token);
		body.put("user", ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()));

		User user = userService.findByUserName(
				((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername());
		MatchUpdateUser userData = new MatchUpdateUser(user);
		body.put("userData", userData);

		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType(ContentTypeConstans.CONTENT_TYPE_JSON);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Usuario o contrasena incorrecto");
		body.put("error", failed.getMessage());

		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401); // 401 no autorizado --- 403 prohibido
		response.setContentType(ContentTypeConstans.CONTENT_TYPE_JSON);

	}

}
