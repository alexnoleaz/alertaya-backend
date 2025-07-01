package com.alertaya.backend.weatheralerts.services;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FakeWeatherService extends WeatherService {
	private final Random rand = new Random();

	public FakeWeatherService(RestTemplate restTemplate, ObjectMapper objectMapper) {
		super(restTemplate, objectMapper);
	}

	@Override
	public Double getRainForNextHour(Double latitude, Double longitude) {
		return switch (rand.nextInt(3)) {
			case 0 -> 0.5;
			case 1 -> 3.0;
			case 2 -> 10.0;
			default -> 0.0;
		};
	}
}
