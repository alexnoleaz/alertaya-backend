package com.alertaya.backend.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alertaya.backend.shared.Response;

@RestControllerAdvice
public class UserExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);

	@ExceptionHandler(InvalidCurrentPasswordException.class)
	public ResponseEntity<Response> handleInvalidCurrentPasswordException(InvalidCurrentPasswordException ex) {
		logger.warn(ex.getMessage());
		return ResponseEntity.badRequest().body(Response.fail(ex.getMessage()));
	}
}
