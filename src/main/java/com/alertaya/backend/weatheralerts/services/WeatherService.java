package com.alertaya.backend.weatheralerts.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// @Service
public class WeatherService {
	@Value("${app.openweather.api-key}")
	private String apiKey;

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public WeatherService(RestTemplate restTemplate, ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public Double getRainForNextHour(Double latitude, Double longitude)
			throws JsonMappingException, JsonProcessingException {
		var url = String.format(
				"https://api.openweathermap.org/data/2.5/onecall?lat=%f&lon=%f&exclude=daily,minutely,current&appid=%s&units=metric",
				latitude, longitude, apiKey);

		var response = restTemplate.getForEntity(url, String.class);
		var root = objectMapper.readTree(response.getBody());
		var firstHour = root.get("hourly").get(0);

		return firstHour.path("rain").path("1h").asDouble(0.0);
	}
}
