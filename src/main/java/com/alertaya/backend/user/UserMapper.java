package com.alertaya.backend.user;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.alertaya.backend.user.dto.CreateUserDto;
import com.alertaya.backend.user.dto.UpdateUserDto;
import com.alertaya.backend.user.dto.UserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
	@Mapping(target = "fullName", expression = "java(entity.getName() + \" \" + entity.getSurname())")
	@Mapping(target = "roleNames", expression = "java(entity.getRoles().stream().map(role -> role.getName()).toArray(String[]::new))")
	UserDto toDto(User entity);

	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "id", ignore = true)
	User toEntity(CreateUserDto dto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromDto(UpdateUserDto dto, @MappingTarget User entity);
}
