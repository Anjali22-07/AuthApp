package com.lcp.auth.auth.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lcp.auth.auth.entities.Role;

public interface RoleRepository extends  JpaRepository<Role, UUID>{

    Optional<Role> findByRole(String roleName);

}
