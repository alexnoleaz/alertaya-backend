package com.alertaya.backend.weatheralerts.model;

import com.alertaya.backend.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "location_alerts")
public class LocationAlert {
	public static final short MAX_NAME_LENGTH = 100;
	public static final short MAX_RAIN_LEVEL_LENGTH = 20;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = MAX_NAME_LENGTH)
	private String name;

	@Column(nullable = false)
	private Double latitude;

	@Column(nullable = false)
	private Double longitude;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = MAX_RAIN_LEVEL_LENGTH)
	private RainLevel alertThreshold;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public RainLevel getAlertThreshold() {
		return alertThreshold;
	}

	public void setAlertThreshold(RainLevel alertThreshold) {
		this.alertThreshold = alertThreshold;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
