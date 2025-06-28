package com.alertaya.backend.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.alertaya.backend.role.Role;

public class CreateRoleDto {
	private String description;

	@NotBlank
	@Size(max = Role.MAX_NAME_LENGTH)
	private String name;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
