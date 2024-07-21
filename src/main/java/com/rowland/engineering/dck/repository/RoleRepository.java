package com.rowland.engineering.dck.repository;

import com.rowland.engineering.dck.model.Role;
import com.rowland.engineering.dck.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String roleName);
    Role findByName(RoleName roleName);

    boolean existsByName(String roleName);
}
