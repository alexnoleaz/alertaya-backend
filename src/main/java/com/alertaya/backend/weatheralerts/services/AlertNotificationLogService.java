package com.alertaya.backend.weatheralerts.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alertaya.backend.shared.exception.EntityNotFoundException;
import com.alertaya.backend.user.User;
import com.alertaya.backend.user.UserRepository;
import com.alertaya.backend.weatheralerts.dto.NotificationLogDto;
import com.alertaya.backend.weatheralerts.repository.AlertNotificationLogRepository;

@Service
public class AlertNotificationLogService {
	private final AlertNotificationLogRepository repository;
	private final UserRepository userRepository;

	public AlertNotificationLogService(AlertNotificationLogRepository repository, UserRepository userRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
	}

	public List<NotificationLogDto> getLogsForUser(Long userId) {
		var user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException(User.class, userId));

		return repository.findByUser(user).stream()
				.map(log -> new NotificationLogDto(
						log.getAlert().getName(),
						log.getRainAmount(),
						log.getSentAt()))
				.toList();
	}
}
