package com.alertaya.backend.weatheralerts.dto;

import java.time.LocalDateTime;

public class NotificationLogDto {
	private String alertName;
	private double rainAmount;
	LocalDateTime sentAt;

	public NotificationLogDto(String alertName, double rainAmount, LocalDateTime sentAt) {
		this.alertName = alertName;
		this.rainAmount = rainAmount;
		this.sentAt = sentAt;
	}

	public String getAlertName() {
		return alertName;
	}

	public double getRainAmount() {
		return rainAmount;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}
}
