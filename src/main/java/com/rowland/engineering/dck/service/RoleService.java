package com.rowland.engineering.dck.service;


import com.rowland.engineering.dck.exception.RoleAlreadyExistException;
import com.rowland.engineering.dck.exception.RoleAssignmentException;
import com.rowland.engineering.dck.exception.UserAlreadyExistsException;
import com.rowland.engineering.dck.model.Role;
import com.rowland.engineering.dck.model.RoleName;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.repository.RoleRepository;
import com.rowland.engineering.dck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }




}
