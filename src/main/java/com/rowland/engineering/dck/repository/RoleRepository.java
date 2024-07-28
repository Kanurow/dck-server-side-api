package com.rowland.engineering.dck.repository;

import com.rowland.engineering.dck.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findByName(String roleName);

    boolean existsByName(String roleName);
}
