package com.alertaya.backend.auth;

public class InvalidCredentialsException extends RuntimeException {
	public InvalidCredentialsException() {
		super("Invalid credentials.");
	}
}
