package com.alertaya.backend.auth;

import com.alertaya.backend.user.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDto {
	@NotBlank
	@Size(max = User.MAX_EMAIL_LENGTH)
	private String email;

	@NotBlank
	@Size(max = User.MAX_PASSWORD_LENGTH)
	private String password;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
