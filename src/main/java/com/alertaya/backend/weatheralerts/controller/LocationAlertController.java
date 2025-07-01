package com.alertaya.backend.weatheralerts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alertaya.backend.shared.Response;
import com.alertaya.backend.weatheralerts.dto.CreateLocationAlertDto;
import com.alertaya.backend.weatheralerts.services.LocationAlertService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Alerts")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RestController
@RequestMapping("/api/alerts")
public class LocationAlertController {
	private final LocationAlertService service;

	public LocationAlertController(LocationAlertService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Response> create(@Valid @RequestBody CreateLocationAlertDto input) {
		return ResponseEntity.ok(Response.success(
				service.create(getCurrentUserId(), input)));
	}

	@GetMapping
	public ResponseEntity<Response> findAlerts() {
		return ResponseEntity.ok(Response.success(service.findUserAlerts(getCurrentUserId())));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteAlert(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok(Response.success());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> updateAlert(
			@PathVariable Long id,
			@Valid @RequestBody CreateLocationAlertDto request) {
		return ResponseEntity.ok(Response.success(service.update(id, request)));
	}

	private Long getCurrentUserId() {
		return Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
}
