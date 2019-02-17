package com.app.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.app.core.auth.filter.JWTAuthenticationFilter;
import com.app.core.auth.filter.JWTAuthorizationFilter;
import com.app.core.auth.service.JWTService;
import com.app.core.models.services.UserDetailService;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true) // para que funciones las anotaciones de los roles en los controladores, prePostEnabled para los preauthorize 
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTService jwtService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.authorizeRequests()
		.antMatchers("/", "/css/**", "/js/**", "/img/**", "/listar**")
		.permitAll()
		//.antMatchers("/ver/**").hasAnyRole("USER")
		.anyRequest()
		.authenticated()
		.and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService)) //registramos el filtro para solicitar persmio con JWT
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService)) //registra el filtro para validar al recibir el token
		
		.csrf().disable() //se deshabilita para evitar el csrf de los formularios de login para usar el JWT
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder build) throws Exception{
		build.userDetailsService(userDetailService)
		.passwordEncoder(passwordEncoder);
	}

}
