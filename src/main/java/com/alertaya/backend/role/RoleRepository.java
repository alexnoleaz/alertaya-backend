package com.alertaya.backend.role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	boolean existsByName(String name);

	List<Role> findByNameIn(Collection<String> names);

	@Query("SELECT r FROM Role r LEFT JOIN FETCH r.users WHERE r.id = :id")
	Optional<Role> findByIdWithUsers(@Param("id") Integer id);
}
