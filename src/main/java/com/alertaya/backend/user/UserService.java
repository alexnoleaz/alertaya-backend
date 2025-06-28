package com.alertaya.backend.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alertaya.backend.role.RoleRepository;
import com.alertaya.backend.shared.exception.EntityNotFoundException;
import com.alertaya.backend.shared.exception.ValueAlreadyUsedException;
import com.alertaya.backend.user.dto.ChangePasswordDto;
import com.alertaya.backend.user.dto.CreateUserDto;
import com.alertaya.backend.user.dto.PagedUserResultDto;
import com.alertaya.backend.user.dto.PagedUserResultRequestDto;
import com.alertaya.backend.user.dto.UpdateUserDto;
import com.alertaya.backend.user.dto.UserDto;

import jakarta.persistence.EntityManager;

@Service
public class UserService {
	private final UserRepository repository;
	private final RoleRepository roleRepository;
	private final UserMapper mapper;
	private final PasswordEncoder passwordEncoder;
	private final EntityManager entityManager;

	public UserService(UserRepository repository, RoleRepository roleRepository, UserMapper mapper,
			PasswordEncoder passwordEncoder,
			EntityManager entityManager) {
		this.repository = repository;
		this.roleRepository = roleRepository;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
		this.entityManager = entityManager;
	}

	public UserDto create(CreateUserDto input) {
		checkEmailIsUnique(input.getEmail(), null);

		var user = mapper.toEntity(input);
		user.setPassword(passwordEncoder.encode(input.getPassword()));

		assignRolesToUser(user, input.getRoleNames(), false);
		repository.save(user);

		return mapper.toDto(user);
	}

	public UserDto find(Long id) {
		return mapper.toDto(findIncludeRolesOrThrow(id));
	}

	public PagedUserResultDto findAll(PagedUserResultRequestDto input) {
		var hasKeyword = input.getKeyword() != null && !input.getKeyword().isBlank();
		var whereClause = hasKeyword
				? " WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
						" OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))"
				: "";
		var idsQuery = entityManager.createQuery("SELECT u.id FROM User u" + whereClause, Long.class);
		var countQuery = entityManager.createQuery("SELECT COUNT(DISTINCT u.id) FROM User u" + whereClause, Long.class);

		if (hasKeyword) {
			idsQuery.setParameter("keyword", input.getKeyword());
			countQuery.setParameter("keyword", input.getKeyword());
		}

		idsQuery.setFirstResult(input.getSkipCount());
		idsQuery.setMaxResults(input.getMaxResultCount());

		return new PagedUserResultDto(
				repository.findByIdsIncludeRoles(idsQuery.getResultList()).stream().map(mapper::toDto).toList(),
				countQuery.getSingleResult());
	}

	public UserDto update(Long id, UpdateUserDto input) {
		checkEmailIsUnique(input.getEmail(), id);

		var dbUser = findIncludeRolesOrThrow(id);

		mapper.updateFromDto(input, dbUser);
		assignRolesToUser(dbUser, input.getRoleNames(), true);
		repository.save(dbUser);

		return mapper.toDto(dbUser);
	}

	public void delete(Long id) {
		var dbUser = findIncludeRolesOrThrow(id);
		dbUser.getRoles().clear();

		repository.save(dbUser);
		repository.delete(dbUser);
	}

	public void changePassword(Long id, ChangePasswordDto input) {
		var dbUser = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(User.class, id));

		if (!passwordEncoder.matches(input.getCurrentPassword(), dbUser.getPassword()))
			throw new InvalidCurrentPasswordException();

		dbUser.setPassword(passwordEncoder.encode(input.getNewPassword()));
		repository.save(dbUser);
	}

	private User findIncludeRolesOrThrow(Long id) {
		return repository.findByIdIncludeRoles(id)
				.orElseThrow(() -> new EntityNotFoundException(User.class, id));
	}

	private void checkEmailIsUnique(String email, Long excludeUserId) {
		boolean exists = excludeUserId == null
				? repository.existsByEmail(email)
				: repository.existsByEmailAndIdNot(email, excludeUserId);

		if (exists)
			throw new ValueAlreadyUsedException("email", email);
	}

	private void assignRolesToUser(User user, String[] roleNames, boolean isUpdate) {
		if (isUpdate)
			user.getRoles().clear();

		if (roleNames == null || roleNames.length == 0)
			return;

		roleRepository.findByNameIn(List.of(roleNames))
				.forEach((role) -> user.getRoles().add(role));
	}
}
