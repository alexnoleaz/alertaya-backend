package com.alertaya.backend.user.dto;

import com.alertaya.backend.user.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserDto {
	@Size(max = User.MAX_NAME_LENGTH)
	private String name;

	@Size(max = User.MAX_SURNAME_LENGTH)
	private String surname;

	@Email
	@Size(max = User.MAX_EMAIL_LENGTH)
	private String email;

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

	public String[] getRoleNames() {
		return roleNames;
	}

	public String getPhone() {
		return phone;
	}
}
