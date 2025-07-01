package com.alertaya.backend.user;

import java.util.HashSet;
import java.util.Set;

import com.alertaya.backend.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users", schema = "public")
public class User {
	public static final short MAX_NAME_LENGTH = 50;
	public static final short MAX_SURNAME_LENGTH = 50;
	public static final short MAX_EMAIL_LENGTH = 100;
	public static final short MAX_PASSWORD_LENGTH = 255;
	public static final short MAX_PHONE_LENGTH = 9;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = MAX_NAME_LENGTH)
	private String name;

	@Column(nullable = false, length = MAX_SURNAME_LENGTH)
	private String surname;

	@Column(nullable = false, length = MAX_EMAIL_LENGTH)
	private String email;

	@Column(nullable = false, length = MAX_PASSWORD_LENGTH)
	private String password;

	@Column(nullable = true, length = MAX_PHONE_LENGTH)
	private String phone;

	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
