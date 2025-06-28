package com.alertaya.backend.role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.alertaya.backend.role.dto.CreateRoleDto;
import com.alertaya.backend.role.dto.RoleDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "users", ignore = true)
	Role toEntity(CreateRoleDto dto);

	RoleDto toDto(Role entity);
}
