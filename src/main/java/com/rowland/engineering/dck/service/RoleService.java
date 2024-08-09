package com.rowland.engineering.dck.service;


import com.rowland.engineering.dck.dto.ApiResponse;
import com.rowland.engineering.dck.dto.CreateRoleRequest;
import com.rowland.engineering.dck.dto.RoleResponse;
import com.rowland.engineering.dck.exception.RoleAlreadyExistException;
import com.rowland.engineering.dck.exception.UserAlreadyExistsException;
import com.rowland.engineering.dck.model.Role;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.repository.RoleRepository;
import com.rowland.engineering.dck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

        System.out.println(roleList);

         return roleList.stream()
                .filter(role -> Objects.equals(role.getName(), "MEMBER") || role.getName().equals("WORKER"))
                .map(role -> new RoleResponse(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }


    @Override
    public void createRole(CreateRoleRequest theRole) {
        String roleName = theRole.getName().toUpperCase();
        Role role = new Role(theRole.getName().toUpperCase());
        if (roleRepository.existsByName(roleName)){
            throw new RoleAlreadyExistException(theRole.getName()+" role already exists");
        }
        roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


    @Override
    public void deleteRole(UUID roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public void removeAllUsersFromRole(UUID roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        roleRepository.save(role.get());
    }

    @Override
    public ResponseEntity<?> removeUserFromRole(UUID userId, UUID roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role>  role = roleRepository.findById(roleId);
        if (role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return new ResponseEntity<>(new ApiResponse(true, "User removed successfully from role!"),
                    HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new ApiResponse(false, "Error! User not removed from role. "),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public User assignRoleToUser(UUID userId, UUID roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role>  role = roleRepository.findById(roleId);
        if (user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(
                    user.get().getFirstName()+ " is already a " + role.get().getName());
        }
        if (role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());
        }
        return user.get();
    }

}
