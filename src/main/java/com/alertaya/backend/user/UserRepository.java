package com.alertaya.backend.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

	boolean existsByEmailAndIdNot(String email, Long id);

	@EntityGraph(attributePaths = { "roles" })
	Optional<User> findByEmail(String email);

	@EntityGraph(attributePaths = { "roles" })
	@Query("SELECT u FROM User u WHERE u.id = :id")
	Optional<User> findByIdIncludeRoles(@Param("id") Long id);

	@EntityGraph(attributePaths = { "roles" })
	@Query("""
				SELECT DISTINCT u FROM User u
				WHERE u.id IN :ids
				ORDER BY u.name ASC
			""")
	List<User> findByIdsIncludeRoles(@Param("ids") List<Long> ids);
}
