package com.alertaya.backend.role;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RoleSeeder implements CommandLineRunner {
	private final RoleRepository repository;

	public RoleSeeder(RoleRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... args) throws Exception {
		if (repository.count() != 0)
			return;

		var admin = new Role();
		admin.setName("ADMIN");
		admin.setDescription("Administrator role");

		var user = new Role();
		user.setName("USER");
		user.setDescription("User role");

		repository.saveAll(List.of(admin, user));
	}
}
