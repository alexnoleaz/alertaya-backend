package com.alertaya.backend.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.alertaya.backend.role.Role;
import com.alertaya.backend.role.RoleRepository;

@Component
@Order(2)
public class UserSeeder implements CommandLineRunner {
	private final UserRepository repository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserSeeder(UserRepository repository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		if (repository.count() != 0)
			return;

		var roleMap = roleRepository.findByNameIn(List.of("ADMIN", "USER")).stream()
				.collect(Collectors.toMap(Role::getName, role -> role));
		var adminRole = roleMap.get("ADMIN");
		var userRole = roleMap.get("USER");

		var admin = new User();
		admin.setName("Admin");
		admin.setSurname("Admin");
		admin.setEmail("admin@alertaya.com");
		admin.setPassword(passwordEncoder.encode("admin123qwe"));
		admin.setRoles(Set.of(adminRole));

		var user = new User();
		user.setName("User");
		user.setSurname("User");
		user.setEmail("user@alertaya.com");
		user.setPassword(passwordEncoder.encode("user123qwe"));
		user.setRoles(Set.of(userRole));

		repository.saveAll(List.of(admin, user));
	}
}
