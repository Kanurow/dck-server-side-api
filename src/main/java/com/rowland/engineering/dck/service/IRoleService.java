package com.rowland.engineering.dck.service;


import com.rowland.engineering.dck.dto.RoleResponse;
import com.rowland.engineering.dck.model.Role;
import com.rowland.engineering.dck.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


public interface IRoleService {
    List<RoleResponse> getRoles();

    Role createRole(Role theRole);

    List<Role> getAllRoles();

    void deleteRole(UUID roleId);
    void removeAllUsersFromRole(UUID roleId);

    ResponseEntity<?> removeUserFromRole(UUID userId, UUID roleId);

    User assignRoleToUser(UUID userId, UUID roleId);

}
