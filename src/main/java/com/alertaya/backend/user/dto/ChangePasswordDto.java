package com.alertaya.backend.user.dto;

import com.alertaya.backend.user.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordDto {
	@NotBlank
	@Size(max = User.MAX_PASSWORD_LENGTH)
	public String currentPassword;

	@NotBlank
	@Size(max = User.MAX_PASSWORD_LENGTH)
	public String newPassword;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
}
