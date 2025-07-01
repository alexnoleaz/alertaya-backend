package com.alertaya.backend.weatheralerts.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alertaya.backend.user.User;
import com.alertaya.backend.weatheralerts.model.AlertNotificationLog;

@Repository
public interface AlertNotificationLogRepository extends JpaRepository<AlertNotificationLog, Long> {
	boolean existsByAlertIdAndSentAtAfter(Long alertId, LocalDateTime after);

	List<AlertNotificationLog> findByUser(User user);
}
