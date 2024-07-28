package com.rowland.engineering.dck.controller;

import com.rowland.engineering.dck.dto.ApiResponse;
import com.rowland.engineering.dck.dto.JwtAuthenticationResponse;
import com.rowland.engineering.dck.dto.LoginRequest;
import com.rowland.engineering.dck.dto.RegisterRequest;

import com.rowland.engineering.dck.model.*;
import com.rowland.engineering.dck.repository.*;
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
    private final DepartmentRepository departmentRepository;
    private final BranchChurchRepository branchChurchRepository;


    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;



    @Operation(
            summary = "Enables user log in - Users can user either phone number or email address"
    )
    @PostMapping("/login")
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
                registerRequest.getPhoneNumber(), registerRequest.getGender());
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Role role = roleRepository.findByName(registerRequest.getRoleName());
        user.setRoles(Collections.singleton(role));

        Department department = departmentRepository.findByName(registerRequest.getDepartment());
        user.setDepartments(Collections.singleton(department));

        BranchChurch branchChurch = branchChurchRepository.findByName(registerRequest.getBranchChurch());
        user.setBranchChurch(Collections.singleton(branchChurch));

        User savedUser = userRepository.save(user);
        System.out.println(savedUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/users/{email}")
                .buildAndExpand(savedUser.getEmail()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }




}
