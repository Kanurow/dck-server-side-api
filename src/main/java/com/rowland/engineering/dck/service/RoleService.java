package com.rowland.engineering.dck.service;


import com.rowland.engineering.dck.dto.RoleResponse;
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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<RoleResponse> getRoles() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream().map(item -> {
            RoleResponse response = new RoleResponse();
            response.setId(item.getId());
            response.setRoleName(item.getName().name());
            return response;
        }).collect(Collectors.toList());
    }

}
