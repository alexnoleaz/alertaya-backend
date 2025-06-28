package com.alertaya.backend.auth;

public class AuthResponse {
	private String accessToken;
	private Long userId;

	public AuthResponse(String accessToken, Long userId) {
		this.accessToken = accessToken;
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public Long getUserId() {
		return userId;
	}
}
