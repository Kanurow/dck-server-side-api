package com.rowland.engineering.dck.controller;


import com.rowland.engineering.dck.dto.ApiResponse;
import com.rowland.engineering.dck.dto.CreateRoleRequest;
import com.rowland.engineering.dck.dto.RoleResponse;
import com.rowland.engineering.dck.exception.RoleAlreadyExistException;
import com.rowland.engineering.dck.model.Role;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;

    @Operation(
            description = "Gets roles for user registration",
            summary = "Gets registration roles"
    )
    @GetMapping("/reg-roles")
    public ResponseEntity<List<RoleResponse>> getRegRoles(){
        return new ResponseEntity<>(roleService.getRoles(), OK);
    }


    @Operation(
            description = "Creates a new role - Must be logged in as an admin or pastor",
            summary = "Creates a new role"
    )
    @PostMapping("/create-new-role")
    @Secured({"PASTOR", "ADMIN"})
    public ResponseEntity<String> createRole(@RequestBody CreateRoleRequest role){
        try{
            roleService.createRole(role);
            return ResponseEntity.ok("New role created successfully!");
        }catch(RoleAlreadyExistException re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());

        }
    }

    @Operation(
            description = "Gets all role - must be logged in as admin or pastor",
            summary = "Gets all roles"
    )
    @GetMapping("/all-roles")
    @Secured({"PASTOR", "ADMIN"})
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getAllRoles(), OK);
    }

    @Operation(
            description = "Deletes role - Must be logged in as an admin or pastor.",
            summary = "Deletes role and removes users having the role authorization from the role"
    )
    @DeleteMapping("/delete/{roleId}")
    @Secured({"PASTOR", "ADMIN"})
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable("roleId") UUID roleId){
        roleService.deleteRole(roleId);
        return new  ResponseEntity<>(new ApiResponse(true, "Success! Role deleted."),
                HttpStatus.OK);
    }



    @Operation(
            description = "Removes user from role - Must be logged in as an admin or pastor.",
            summary = "Removes a user from role"
    )
    @Secured({"PASTOR", "ADMIN"})
    @DeleteMapping("/remove-user-from-role")
    public ResponseEntity<?> removeUserFromRole(
            @RequestParam("userId") UUID userId,
            @RequestParam("roleId") UUID roleId){
        return roleService.removeUserFromRole(userId, roleId);
    }

    @Operation(
            description = "Assigns role to a user - Must be logged in as an admin or pastor.",
            summary = "Assigns role to a user"
    )
    @Secured({"PASTOR", "ADMIN"})
    @PostMapping("/assign-user-to-role")
    public User assignUserToRole(
            @RequestParam("userId") UUID userId,
            @RequestParam("roleId") UUID roleId){
        return roleService.assignRoleToUser(userId, roleId);
    }


}
