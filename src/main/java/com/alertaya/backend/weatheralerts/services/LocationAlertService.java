package com.alertaya.backend.weatheralerts.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alertaya.backend.shared.exception.EntityNotFoundException;
import com.alertaya.backend.user.User;
import com.alertaya.backend.user.UserRepository;
import com.alertaya.backend.weatheralerts.dto.CreateLocationAlertDto;
import com.alertaya.backend.weatheralerts.dto.LocationAlertDto;
import com.alertaya.backend.weatheralerts.dto.LocationAlertMapper;
import com.alertaya.backend.weatheralerts.model.LocationAlert;
import com.alertaya.backend.weatheralerts.repository.LocationAlertRepository;

@Service
public class LocationAlertService {
	private final LocationAlertRepository repository;
	private final LocationAlertMapper mapper;
	private final UserRepository userRepository;

	public LocationAlertService(LocationAlertRepository repository, LocationAlertMapper mapper,
			UserRepository userRepository) {
		this.repository = repository;
		this.mapper = mapper;
		this.userRepository = userRepository;
	}

	public LocationAlertDto create(Long userId, CreateLocationAlertDto input) {
		var dbUser = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException(User.class, userId));

		var exists = repository.existsByUserIdAndLatitudeAndLongitude(userId, input.getLatitude(),
				input.getLongitude());
		if (exists)
			throw new IllegalArgumentException("Location alert already exists");

		var alert = mapper.toEntity(input);
		alert.setUser(dbUser);

		return mapper.toDto(repository.save(alert));
	}

	public List<LocationAlertDto> findUserAlerts(Long userId) {
		return repository.findByUserId(userId).stream().map(mapper::toDto).toList();
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public LocationAlertDto update(Long id, CreateLocationAlertDto input) {
		var alert = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(LocationAlert.class, id));

		mapper.updateFromDto(input, alert);
		repository.save(alert);

		return mapper.toDto(alert);
	}
}
