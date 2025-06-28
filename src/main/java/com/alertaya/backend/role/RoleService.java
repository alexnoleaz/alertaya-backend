package com.alertaya.backend.role;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alertaya.backend.role.dto.CreateRoleDto;
import com.alertaya.backend.role.dto.RoleDto;
import com.alertaya.backend.shared.exception.EntityNotFoundException;
import com.alertaya.backend.shared.exception.ValueAlreadyUsedException;

@Service
public class RoleService {
	private final RoleRepository repository;
	private final RoleMapper mapper;

	public RoleService(RoleRepository repository, RoleMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public RoleDto create(CreateRoleDto input) {
		if (repository.existsByName(input.getName()))
			throw new ValueAlreadyUsedException("name", input.getName());

		return mapper.toDto(repository.save(mapper.toEntity(input)));
	}

	public RoleDto find(Integer id) {
		return mapper.toDto(repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Role.class, id)));
	}

	public List<RoleDto> findAll() {
		return repository.findAll().stream().map(mapper::toDto).toList();
	}

	public void delete(Integer id) {
		var dbRole = repository.findByIdWithUsers(id)
				.orElseThrow(() -> new EntityNotFoundException(Role.class, id));

		dbRole.getUsers().forEach(user -> user.getRoles().remove(dbRole));
		dbRole.getUsers().clear();

		repository.save(dbRole);
		repository.delete(dbRole);
	}
}
