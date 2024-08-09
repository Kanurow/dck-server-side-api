package com.rowland.engineering.dck.service;

import com.rowland.engineering.dck.dto.UpdateUserInformation;
import com.rowland.engineering.dck.dto.UserDetailsResponse;
import com.rowland.engineering.dck.dto.UserProfileInfo;
import com.rowland.engineering.dck.exception.CellUnitNotFoundException;
import com.rowland.engineering.dck.exception.UserNotFoundException;
import com.rowland.engineering.dck.model.CellUnit;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.repository.CellUnitRepository;
import com.rowland.engineering.dck.repository.UserRepository;
import com.rowland.engineering.dck.security.UserPrincipal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final CellUnitRepository cellUnitRepository;


    public List<UserDetailsResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
            userDetailsResponse.setId(user.getId());
            userDetailsResponse.setEmail(user.getEmail());
            userDetailsResponse.setFirstName(user.getFirstName());
            userDetailsResponse.setLastName(user.getLastName());
            userDetailsResponse.setPhoneNumber(user.getPhoneNumber());
            userDetailsResponse.setCellLeader(user.isCellLeader());
            userDetailsResponse.setGender(user.getGender());
            userDetailsResponse.setDepartments(user.getDepartments());
            userDetailsResponse.setBranchChurch(user.getBranchChurch());
            userDetailsResponse.setDateOfBirth(user.getDateOfBirth());
            userDetailsResponse.setFavouriteBiblePassage(user.getFavouriteBiblePassage());
            userDetailsResponse.setCellUnitId(user.getCellUnitId());
            userDetailsResponse.setRoles(user.getRoles());
            return userDetailsResponse;
        }).collect(Collectors.toList());

    }


    public User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist"));
    }

    @Transactional
    @Override
    public ResponseEntity<String> updateUserInformation(UpdateUserInformation updateUserInformation, UserPrincipal currentUser) {
        try {
            User foundUser = userRepository.findById(currentUser.getId()).orElseThrow(() -> new UserNotFoundException("User does not exist"));
            foundUser.setFirstName(updateUserInformation.getFirstName());
            foundUser.setLastName(updateUserInformation.getLastName());
            foundUser.setGender(updateUserInformation.getGender());
            foundUser.setAlternativePhoneNumber(updateUserInformation.getAlternativePhoneNumber());
            foundUser.setDateOfBirth(updateUserInformation.getDateOfBirth());
            foundUser.setFavouriteBiblePassage(updateUserInformation.getFavouriteBiblePassage());
            User savedUserUpdatedInfo = userRepository.save(foundUser);
            return ResponseEntity.status(HttpStatus.OK).body(savedUserUpdatedInfo.getFirstName() + " has updated profile information");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update new user details.");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> makeCellLeader(UUID userId, UUID cellUnitId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        CellUnit foundCellUnit = cellUnitRepository.findById(cellUnitId).orElseThrow(() -> new CellUnitNotFoundException("Cell unit not found"));

        foundUser.setCellLeader(true);
        foundCellUnit.setLeaderId(foundUser.getId());
        userRepository.save(foundUser);
        cellUnitRepository.save(foundCellUnit);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser.getFirstName() + " is now a cell leader");
    }

    @Transactional
    @Override
    public UserProfileInfo getUserProfileDetails(UUID currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return UserProfileInfo.builder()
                .id(currentUser.getId())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .phoneNumber(currentUser.getPhoneNumber())
                .alternativePhoneNumber(currentUser.getAlternativePhoneNumber())
                .gender(currentUser.getGender())
                .walletBalance(currentUser.getWalletBalance())
                .branchChurch(currentUser.getBranchChurch())
                .roles(currentUser.getRoles())
                .dateOfBirth(currentUser.getDateOfBirth())
                .favouriteBiblePassage(currentUser.getFavouriteBiblePassage())
                .email(currentUser.getEmail())
                .build();
    }

}
