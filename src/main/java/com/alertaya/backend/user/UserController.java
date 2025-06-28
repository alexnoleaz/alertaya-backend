package com.alertaya.backend.user;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alertaya.backend.shared.Response;
import com.alertaya.backend.user.dto.ChangePasswordDto;
import com.alertaya.backend.user.dto.PagedUserResultRequestDto;
import com.alertaya.backend.user.dto.UpdateUserDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Users")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> find(@PathVariable("id") Long id) {
		return ResponseEntity.ok(Response.success(service.find(id)));
	}

	@GetMapping
	public ResponseEntity<Response> findAll(@Valid @ParameterObject PagedUserResultRequestDto input) {
		return ResponseEntity.ok(Response.success(service.findAll(input)));
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/{id}")
	public ResponseEntity<Response> update(@PathVariable("id") Long id,
			@Valid @RequestBody UpdateUserDto input) {
		return ResponseEntity.ok(Response.success(service.update(id, input)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok(Response.success());
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/{id}/change-password")
	public ResponseEntity<Response> changePassword(@PathVariable("id") Long id,
			@Valid @RequestBody ChangePasswordDto input) {
		service.changePassword(id, input);
		return ResponseEntity.ok(Response.success());
	}
}
