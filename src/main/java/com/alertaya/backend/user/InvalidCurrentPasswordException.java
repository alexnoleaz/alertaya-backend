package com.alertaya.backend.user;

public class InvalidCurrentPasswordException extends RuntimeException {
	public InvalidCurrentPasswordException() {
		super("The current password is invalid.");
	}
}
