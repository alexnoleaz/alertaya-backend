package com.alertaya.backend.weatheralerts.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alertaya.backend.weatheralerts.repository.LocationAlertRepository;

@Service
public class AlertEvaluatorService {
	private final LocationAlertRepository repository;
	private final WeatherService weatherService;
	private final NotificationService notificationService;
	private final Logger logger = LoggerFactory.getLogger(AlertEvaluatorService.class);

	public AlertEvaluatorService(LocationAlertRepository repository, WeatherService weatherService,
			NotificationService notificationService) {
		this.repository = repository;
		this.weatherService = weatherService;
		this.notificationService = notificationService;
	}

	@Scheduled(fixedRate = 30 * 60 * 1000)
	public void evaluateAlerts() {
		var alerts = repository.findAll();
		for (var alert : alerts) {
			try {
				var rain = weatherService.getRainForNextHour(alert.getLatitude(), alert.getLongitude());
				var shouldNotify = switch (alert.getAlertThreshold()) {
					case LIGHT -> rain > 0.0;
					case MODERATE -> rain >= 1.5;
					case HEAVY -> rain >= 7.0;
				};

				if (shouldNotify && !notificationService.alreadyNotifiedRecently(alert.getId(), 60))
					notificationService.sendAlert(alert.getUser(), alert, rain);

			} catch (Exception e) {
				logger.error("Error al evaluar alerta ID " + alert.getId() + ": " + e.getMessage(), e);
			}
		}
	}
}
