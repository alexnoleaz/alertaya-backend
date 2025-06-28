package com.alertaya.backend.user.dto;

public class UserDto {
	private Long id;
	private String name;
	private String surname;
	private String fullName;
	private String email;
	private String[] roleNames;

	public UserDto(Long id, String name, String surname, String fullName, String email, String[] roleNames) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.fullName = fullName;
		this.email = email;
		this.roleNames = roleNames;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getFullName() {
		return fullName;
	}

	public String getEmail() {
		return email;
	}

	public String[] getRoleNames() {
		return roleNames;
	}
}
