package com.alertaya.backend.weatheralerts.dto;

import com.alertaya.backend.weatheralerts.model.RainLevel;

public class LocationAlertDto {
	private Long id;
	private String name;
	private Double latitude;
	private Double longitude;
	private RainLevel alertThreshold;

	public LocationAlertDto(Long id, String name, Double latitude, Double longitude, RainLevel alertThreshold) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.alertThreshold = alertThreshold;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public RainLevel getAlertThreshold() {
		return alertThreshold;
	}
}
