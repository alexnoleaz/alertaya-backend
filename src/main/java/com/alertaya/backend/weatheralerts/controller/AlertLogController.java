package com.alertaya.backend.weatheralerts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alertaya.backend.shared.Response;
import com.alertaya.backend.weatheralerts.services.AlertNotificationLogService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Alert Logs")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RestController
@RequestMapping("/api/alerts/logs")
public class AlertLogController {
	private final AlertNotificationLogService service;

	public AlertLogController(AlertNotificationLogService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Response> getLogs() {
		var userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return ResponseEntity.ok(Response.success(service.getLogsForUser(userId)));
	}
}
