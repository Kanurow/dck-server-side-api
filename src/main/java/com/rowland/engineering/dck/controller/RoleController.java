package com.rowland.engineering.dck.controller;


import com.rowland.engineering.dck.exception.RoleAlreadyExistException;
import com.rowland.engineering.dck.model.Role;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.http.HttpStatus.FOUND;


@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;

    @Operation(
            description = "Gets all roles",
            summary = "Gets all roles"
    )
    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getRoles(), FOUND);
    }

    @Operation(
            description = "Creates a new role - just role name is needed",
            summary = "create new role"
    )
    @PostMapping("/create-new-role")
    public ResponseEntity<String> createRole(@RequestBody Role theRole){
        try{
            roleService.createRole(theRole);
            return ResponseEntity.ok("New role created successfully!");
        }catch(RoleAlreadyExistException re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());
        }
    }
    @Operation(
            description = "Delete role from db - must have an admin level role",
            summary = "Delete role"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
        roleService.deleteRole(roleId);
    }

    @Operation(
            description = "Removes all users from a given role",
            summary = "Removes all users from a given role"
    )
    @PostMapping("/remove-all-users-from-role/{roleId}")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId){
        return roleService.removeAllUsersFromRole(roleId);
    }

    @Operation(
            description = "Removes a user from role",
            summary = "Removes a user from role"
    )
    @DeleteMapping("/remove-user-from-role")
    public User removeUserFromRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId){
        return roleService.removeUserFromRole(userId, roleId);
    }

    @Operation(
            description = "Assigns role to user for authorization purposes",
            summary = "Assigns role to a user"
    )
    @PostMapping("/assign-user-to-role")
    public User assignUserToRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId){
        return roleService.assignRoleToUser(userId, roleId);
    }
}
