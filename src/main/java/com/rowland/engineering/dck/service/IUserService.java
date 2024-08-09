package com.rowland.engineering.dck.service;

import com.rowland.engineering.dck.dto.UpdateUserInformation;
import com.rowland.engineering.dck.dto.UserDetailsResponse;
import com.rowland.engineering.dck.dto.UserProfileInfo;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.security.CurrentUser;
import com.rowland.engineering.dck.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<UserDetailsResponse> getAllUsers();
    User findUserById(UUID id);

    ResponseEntity<String> updateUserInformation(UpdateUserInformation updateUserInformation, UserPrincipal currentUser);

    ResponseEntity<String> makeCellLeader(UUID userId, UUID cellUnitId);

    UserProfileInfo getUserProfileDetails(UUID currentUserId);
}
