package com.rowland.engineering.dck.service;

import com.rowland.engineering.dck.dto.UpdateUserInformation;
import com.rowland.engineering.dck.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    Optional<User> findUserById(Long id);

    ResponseEntity<String> updateUserInformation(UpdateUserInformation updateUserInformation);

    ResponseEntity<String> makeCellLeader(Long userId, Long cellUnitId);
}
