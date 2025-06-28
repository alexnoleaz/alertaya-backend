package com.alertaya.backend.role;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alertaya.backend.role.dto.CreateRoleDto;
import com.alertaya.backend.shared.Response;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Roles")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/roles")
public class RoleController {
	private final RoleService service;

	public RoleController(RoleService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Response> create(@Valid @RequestBody CreateRoleDto input) {
		var role = service.create(input);
		return ResponseEntity.created(URI.create(String.format("/api/roles/%d", role.getId())))
				.body(Response.success(role, HttpStatus.CREATED));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> find(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(Response.success(service.find(id)));
	}

	@GetMapping
	public ResponseEntity<Response> findAll() {
		return ResponseEntity.ok(Response.success(service.findAll()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@PathVariable("id") Integer id) {
		service.delete(id);
		return ResponseEntity.ok(Response.success());
	}
}
