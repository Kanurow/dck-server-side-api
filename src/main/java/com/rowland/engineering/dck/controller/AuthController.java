package com.rowland.engineering.dck.controller;

import com.rowland.engineering.dck.dto.ApiResponse;
import com.rowland.engineering.dck.dto.JwtAuthenticationResponse;
import com.rowland.engineering.dck.dto.LoginRequest;
import com.rowland.engineering.dck.dto.RegisterRequest;

import com.rowland.engineering.dck.model.Role;
import com.rowland.engineering.dck.model.RoleName;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.repository.CellUnitRepository;
import com.rowland.engineering.dck.repository.RoleRepository;
import com.rowland.engineering.dck.repository.UserRepository;
import com.rowland.engineering.dck.security.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;



@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication - Registration /Sign In")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CellUnitRepository cellUnitRepository;


    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;



    @Operation(
            summary = "Enables user log in - Users can user either phone number or email address"
    )
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmailOrPhoneNumber(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(
                jwt));
    }

    @Operation(
            summary = "Enables user registration - To sign up with admin role, add `row` to email field."
    )
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if(userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            return new ResponseEntity<>(new ApiResponse(false, "Phone number is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(registerRequest.getFirstName(), registerRequest.getLastName(),
                registerRequest.getDateOfBirth(), registerRequest.getEmail(),
                registerRequest.getPhoneNumber(), registerRequest.getBranchChurch(), registerRequest.getGender(),
                registerRequest.getPassword());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));


        RoleName userRole = getRoleFromRequest(registerRequest.getRoleName());
        Role role = roleRepository.findByName(userRole);

        user.setRoles(Collections.singleton(role));
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/users/{email}")
                .buildAndExpand(savedUser.getEmail()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    private RoleName getRoleFromRequest(String roleName) {
        return switch (roleName) {
            case "ROLE_MEMBER" -> RoleName.ROLE_MEMBER;
            case "ROLE_WORKER" -> RoleName.ROLE_WORKER;
            case "ROLE_CELL_LEADER" -> RoleName.ROLE_CELL_LEADER;
            case "ROLE_PASTOR" -> RoleName.ROLE_PASTOR;
            case "ROLE_ADMIN__USER" -> RoleName.ROLE_ADMIN__USER;
            default -> throw new IllegalArgumentException("Invalid role name: " + roleName);
        };
    }


}
