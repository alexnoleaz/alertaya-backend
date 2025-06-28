package com.alertaya.backend.shared.exception;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alertaya.backend.shared.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Response> handleNotFoundException(EntityNotFoundException ex) {
		logger.warn(ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Response.fail(ex.getMessage(), HttpStatus.NOT_FOUND));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		logger.warn(ex.getMessage());

		return ResponseEntity.badRequest()
				.body(Response.fail(ex.getBindingResult().getFieldErrors().stream()
						.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))));
	}

	@ExceptionHandler(ValueAlreadyUsedException.class)
	public ResponseEntity<Response> handleValueAlreadyUsedException(ValueAlreadyUsedException ex) {
		logger.warn(ex.getMessage());

		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(Response.fail(ex.getMessage(), HttpStatus.CONFLICT));
	}
}
