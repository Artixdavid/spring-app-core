package com.app.core.auth.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.core.auth.SimpleGrantedAuthorityMixin;
import com.app.core.constants.TokenConstans;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JWTServiceImpl implements JWTService {

	@Override
	public String create(Authentication auth) throws JsonProcessingException {
		String username = ((User) auth.getPrincipal()).getUsername();

		Collection<? extends GrantedAuthority> role = auth.getAuthorities();

		Claims claims = this.setClaims(role);

		String token = this.createJWT(username, claims);

		return token;
	}

	@Override
	public boolean validate(String token) {
		try {
			getClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public Claims getClaims(String token) {
		Claims claimsToken = Jwts.parser().setSigningKey(TokenConstans.SECRET_KEY).parseClaimsJws(resolve(token))
				.getBody();
		return claimsToken;
	}

	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {

		Object role = getClaims(token).get(TokenConstans.ROLE_TOKEN);

		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
						.readValue(role.toString().getBytes(), SimpleGrantedAuthority[].class));
		return authorities;
	}

	@Override
	public String resolve(String token) {
		if (token != null && token.startsWith(TokenConstans.PREFIX_TOKEN)) {
			return token.replace(TokenConstans.PREFIX_TOKEN, "");
		}
		return null;
	}

	@Override
	public String createJWT(String username, Claims claims) {
		return Jwts.builder().setClaims(claims).setSubject(username).signWith(TokenConstans.SECRET_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + TokenConstans.EXPIRATION_DATE)).compact();
	}

	@Override
	public Claims setClaims(Collection<? extends GrantedAuthority> role) {
		Claims claims = Jwts.claims();
		try {
			claims.put(TokenConstans.ROLE_TOKEN, new ObjectMapper().writeValueAsString(role));
		} catch (JsonProcessingException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		return claims;
	}

}
