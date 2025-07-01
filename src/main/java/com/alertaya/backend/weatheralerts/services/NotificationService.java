package com.alertaya.backend.weatheralerts.services;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.alertaya.backend.user.User;
import com.alertaya.backend.weatheralerts.model.AlertNotificationLog;
import com.alertaya.backend.weatheralerts.model.LocationAlert;
import com.alertaya.backend.weatheralerts.model.RainLevel;
import com.alertaya.backend.weatheralerts.repository.AlertNotificationLogRepository;

@Service
public class NotificationService {
	private final JavaMailSender mailSender;
	private final AlertNotificationLogRepository logRepository;

	public NotificationService(JavaMailSender mailSender, AlertNotificationLogRepository logRepository) {
		this.mailSender = mailSender;
		this.logRepository = logRepository;
	}

	public boolean alreadyNotifiedRecently(Long alertId, int minutes) {
		LocalDateTime cutoff = LocalDateTime.now().minusMinutes(minutes);
		return logRepository.existsByAlertIdAndSentAtAfter(alertId, cutoff);
	}

	public void sendAlert(User user, LocationAlert alert, double rainAmount) {
		var subject = "AlertaYa -  Alerta de Lluvia para " + alert.getName();
		var body = String.format("""
				Estimado/a %s,

				Se pronostica una posible lluvia de %.2f mm en la ubicación "%s".

				Umbral configurado: %s
				Acción recomendada: tome precauciones de acuerdo a su plan de prevención.

				Atentamente,
				AlertaYa
				""", user.getName(), rainAmount, alert.getName(), translateRainLevel(alert.getAlertThreshold()));

		var message = new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setSubject(subject);
		message.setText(body);

		mailSender.send(message);

		var log = new AlertNotificationLog();
		log.setUser(user);
		log.setAlert(alert);
		log.setRainAmount(rainAmount);
		log.setSentAt(LocalDateTime.now());
		logRepository.save(log);
	}

	private String translateRainLevel(RainLevel level) {
		return switch (level) {
			case LIGHT -> "Ligera";
			case MODERATE -> "Moderada";
			case HEAVY -> "Fuerte";
		};
	}
}
