package com.alertaya.backend.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alertaya.backend.shared.Response;

@RestControllerAdvice
public class AuthExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(AuthExceptionHandler.class);

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<Response> handleInvalidCredentialsException(InvalidCredentialsException ex) {
		logger.warn(ex.getMessage());
		return ResponseEntity.badRequest().body(Response.fail(ex.getMessage()));
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<Response> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
		logger.warn(ex.getMessage());

		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Response.fail(ex.getMessage(), HttpStatus.FORBIDDEN));
	}
}
