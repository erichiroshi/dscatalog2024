package com.erichiroshi.dscatalog.controllers;

import org.bouncycastle.openssl.PasswordException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.erichiroshi.dscatalog.config.JwtUtil;
import com.erichiroshi.dscatalog.entities.dtos.AuthenticationRequest;
import com.erichiroshi.dscatalog.entities.dtos.JwtToken;
import com.erichiroshi.dscatalog.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
public class AuthController {

	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final UserService userDetailsService;

	public AuthController(BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserService userDetailsService) {
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/oauth/token")
	public JwtToken createToken(@RequestBody AuthenticationRequest request) throws PasswordException {
		log.info("createToken(-)");

		// Authenticate the user
		UserDetails user = userDetailsService.loadUserByUsername(request.username());
		try {
			if (!passwordEncoder.matches(request.password(), user.getPassword())) {
				throw new PasswordException("Password invalid");
			}
		} catch (IllegalArgumentException e) {
			throw new PasswordException("Password invalid");
		}

		// Generate the token
		return new JwtToken(jwtUtil.generateToken(request.username()));
	}
}