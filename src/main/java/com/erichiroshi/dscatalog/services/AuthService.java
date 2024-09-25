package com.erichiroshi.dscatalog.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.erichiroshi.dscatalog.entities.User;
import com.erichiroshi.dscatalog.repositories.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;

	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User authenticated() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(username);
	}
}
