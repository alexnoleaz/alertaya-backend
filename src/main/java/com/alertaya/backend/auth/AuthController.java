package com.alertaya.backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alertaya.backend.auth.jwtbearer.JwtService;
import com.alertaya.backend.shared.Response;
import com.alertaya.backend.user.dto.CreateUserDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Auth")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final JwtService jwtService;
	private final AuthService service;

	public AuthController(JwtService jwtService, AuthService service) {
		this.jwtService = jwtService;
		this.service = service;
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@Valid @RequestBody LoginRequestDto input) {
		var user = service.login(input);
		var token = jwtService.generate(user.getId(), user.getRoleNames());

		return ResponseEntity.ok(Response.success(new AuthResponse(token, user.getId())));
	}

	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody CreateUserDto input) {
		var user = service.register(input);
		var token = jwtService.generate(user.getId(), user.getRoleNames());

		return ResponseEntity.ok(Response.success(new AuthResponse(token, user.getId()), HttpStatus.CREATED));
	}
}
