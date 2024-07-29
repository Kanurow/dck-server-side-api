package com.rowland.engineering.dck.controller;


import com.rowland.engineering.dck.dto.ApiResponse;
import com.rowland.engineering.dck.dto.RoleResponse;
import com.rowland.engineering.dck.exception.RoleAlreadyExistException;
import com.rowland.engineering.dck.model.Role;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


import static org.springframework.http.HttpStatus.FOUND;
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
            description = "Creates a new role",
            summary = "Gets all roles"
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
            description = "Gets roles for user registration",
            summary = "Gets registration roles"
    )
    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getAllRoles(), OK);
    }

    @DeleteMapping("/delete/{roleId}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable("roleId") UUID roleId){
        roleService.deleteRole(roleId);
        return new  ResponseEntity<>(new ApiResponse(true, "Success! Role deleted."),
                HttpStatus.OK);
    }



    @DeleteMapping("/remove-user-from-role")
    public ResponseEntity<?> removeUserFromRole(
            @RequestParam("userId") UUID userId,
            @RequestParam("roleId") UUID roleId){
        return roleService.removeUserFromRole(userId, roleId);
    }

    @PostMapping("/assign-user-to-role")
    public User assignUserToRole(
            @RequestParam("userId") UUID userId,
            @RequestParam("roleId") UUID roleId){
        return roleService.assignRoleToUser(userId, roleId);
    }


}
