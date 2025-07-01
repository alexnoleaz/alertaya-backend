package com.alertaya.backend.weatheralerts.model;

import java.time.LocalDateTime;

import com.alertaya.backend.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class AlertNotificationLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private User user;

	@ManyToOne(optional = false)
	private LocationAlert alert;

	@Column(nullable = false)
	private double rainAmount;

	@Column(nullable = false)
	private LocalDateTime sentAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocationAlert getAlert() {
		return alert;
	}

	public void setAlert(LocationAlert alert) {
		this.alert = alert;
	}

	public double getRainAmount() {
		return rainAmount;
	}

	public void setRainAmount(double rainAmount) {
		this.rainAmount = rainAmount;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
}
