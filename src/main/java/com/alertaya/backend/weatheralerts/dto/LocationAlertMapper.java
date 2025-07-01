package com.alertaya.backend.weatheralerts.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.alertaya.backend.weatheralerts.model.LocationAlert;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationAlertMapper {
	LocationAlertDto toDto(LocationAlert entity);

	LocationAlert toEntity(CreateLocationAlertDto dto);

	void updateFromDto(CreateLocationAlertDto dto, @MappingTarget LocationAlert entity);
}
