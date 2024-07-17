package com.rowland.engineering.dck.service;

import com.rowland.engineering.dck.dto.UpdateUserInformation;
import com.rowland.engineering.dck.exception.EntityNotFoundException;
import com.rowland.engineering.dck.exception.UserNotFoundException;
import com.rowland.engineering.dck.model.CellUnit;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.repository.CellUnitRepository;
import com.rowland.engineering.dck.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final CellUnitRepository cellUnitRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public ResponseEntity<String> updateUserInformation(UpdateUserInformation updateUserInformation) {
        try {
            User foundUser = userRepository.findById(updateUserInformation.getUserId()).orElseThrow(() -> new UserNotFoundException("User does not exist"));
            foundUser.setFirstName(updateUserInformation.getFirstName());
            foundUser.setLastName(updateUserInformation.getLastName());
            foundUser.setBranchChurch(updateUserInformation.getBranchChurch());
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
    public ResponseEntity<String> makeCellLeader(Long userId, Long cellUnitId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        CellUnit foundCellUnit = cellUnitRepository.findById(cellUnitId).orElseThrow(() -> new EntityNotFoundException("Cell unit not found"));

        foundUser.setCellLeader(true);
        foundCellUnit.setCellLeaderId(foundUser.getId());

        userRepository.save(foundUser);
        cellUnitRepository.save(foundCellUnit);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser.getFirstName() + " is now a cell leader");
    }
}
