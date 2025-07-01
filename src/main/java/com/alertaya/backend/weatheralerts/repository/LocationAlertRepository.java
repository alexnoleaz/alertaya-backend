package com.alertaya.backend.weatheralerts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alertaya.backend.weatheralerts.model.LocationAlert;

@Repository
public interface LocationAlertRepository extends JpaRepository<LocationAlert, Long> {
	List<LocationAlert> findByUserId(Long userId);

	boolean existsByUserIdAndLatitudeAndLongitude(Long userId, double latitude, double longitude);
}
