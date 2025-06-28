package com.alertaya.backend.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alertaya.backend.user.UserMapper;
import com.alertaya.backend.user.UserRepository;
import com.alertaya.backend.user.UserService;
import com.alertaya.backend.user.dto.CreateUserDto;
import com.alertaya.backend.user.dto.UserDto;

@Service
public class AuthService {
	private final UserService service;
	private final UserRepository repository;
	private final UserMapper mapper;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserService userService, UserRepository userRepository, UserMapper mapper,
			PasswordEncoder passwordEncoder) {
		this.service = userService;
		this.repository = userRepository;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
	}

	public UserDto login(LoginRequestDto input) {
		var dbUser = repository.findByEmail(input.getEmail())
				.orElseThrow(() -> new InvalidCredentialsException());

		if (!passwordEncoder.matches(input.getPassword(), dbUser.getPassword()))
			throw new InvalidCredentialsException();

		return mapper.toDto(dbUser);
	}

	public UserDto register(CreateUserDto input) {
		return service.create(input);
	}

}
