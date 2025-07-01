package com.alertaya.backend.weatheralerts.dto;

import com.alertaya.backend.weatheralerts.model.LocationAlert;
import com.alertaya.backend.weatheralerts.model.RainLevel;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateLocationAlertDto {
	@NotBlank
	@Size(max = LocationAlert.MAX_NAME_LENGTH)
	private String name;

	@NotNull
	@DecimalMin(value = "-90.0", inclusive = true)
	@DecimalMax(value = "90.0", inclusive = true)
	private Double latitude;

	@NotNull()
	@DecimalMin(value = "-180.0", inclusive = true)
	@DecimalMax(value = "180.0", inclusive = true)
	private Double longitude;

	@NotNull
	private RainLevel alertThreshold;

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
