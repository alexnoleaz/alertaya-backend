package com.alertaya.backend.user.dto;

import com.alertaya.backend.user.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserDto {
	@NotBlank
	@Size(max = User.MAX_NAME_LENGTH)
	private String name;

	@NotBlank
	@Size(max = User.MAX_SURNAME_LENGTH)
	private String surname;

	@Email
	@NotBlank
	@Size(max = User.MAX_EMAIL_LENGTH)
	private String email;

	@NotBlank
	@Size(max = User.MAX_PASSWORD_LENGTH)
	private String password;

	@Size(max = User.MAX_PHONE_LENGTH)
	private String phone;

	private String[] roleNames;

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String[] getRoleNames() {
		return roleNames;
	}
}
