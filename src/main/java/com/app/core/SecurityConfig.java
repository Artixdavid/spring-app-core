package com.app.core;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.app.core.auth.filter.JWTAuthenticationFilter;
import com.app.core.auth.filter.JWTAuthorizationFilter;
import com.app.core.auth.service.JWTService;
import com.app.core.models.services.UserDetailService;
import com.app.core.models.services.UserServiceImpl;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true) // para que funciones las anotaciones de los roles en los controladores, prePostEnabled para los preauthorize 
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private UserServiceImpl userService;
	
	
//	@Bean
//    CorsFilter corsFilter() {
//        CorsFilter filter = new CorsFilter();
//        return filter;
//    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http
		
		.cors().and().authorizeRequests() //colocar el CORS().AND para que funcione en angular
		
		
//		
//		.addFilterBefore(corsFilter(), SessionManagementFilter.class)
//		.authorizeRequests()
		
		
//		 
//		.cors().configurationSource(corsConfigurationSource())
//		.and()
//		.addFilterBefore(corsFilter(), SessionManagementFilter.class)
//		.authorizeRequests()
		
		
		
		//cors().and().authorizeRequests() //colocar el CORS().AND para que funcione en angular
		//.antMatchers("/","/api/login").hasAnyRole("USER")
		//.antMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("USER")
		.antMatchers("/api/users").hasAnyRole("USER")
		.anyRequest()
		.permitAll()
		//.antMatchers("/ver/**").hasAnyRole("USER")
		.anyRequest()
		.authenticated()
		.and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService, userService)) //registramos el filtro para solicitar persmio con JWT
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService)) //registra el filtro para validar al recibir el token
		
		.csrf().disable() //se deshabilita para evitar el csrf de los formularios de login para usar el JWT
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        

//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS, HEADERS");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Max-Age", "180");
        
        
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "**"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Credentials", "true"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "*"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Max-Age", "180"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder build) throws Exception{
		build.userDetailsService(userDetailService)
		.passwordEncoder(passwordEncoder);
	}

}
