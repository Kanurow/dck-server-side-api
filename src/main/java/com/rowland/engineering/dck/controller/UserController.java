package com.rowland.engineering.dck.controller;

import com.rowland.engineering.dck.dto.UpdateUserInformation;
import com.rowland.engineering.dck.dto.UserSummary;
import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.security.CurrentUser;
import com.rowland.engineering.dck.security.UserPrincipal;
import com.rowland.engineering.dck.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final IUserService userService;
    @Operation(
            summary = "Gets summary data of actively logged in user"
    )
    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@Valid  @CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getFirstName(), currentUser.getLastName(),
                currentUser.getPhoneNumber(), currentUser.getEmail());
    }

    @Operation(
            description = "Get user by Id",
            summary = "Returns user by providing user id"
    )
    @GetMapping("/user/{userId}")
    public Optional<User> getUserById(@PathVariable(value = "userId") UUID id) {
        return userService.findUserById(id);
    }

//    @PreAuthorize("hasRole('ROLE_PASTOR') or hasRole('ROLE_ADMIN__USER')")
    @Operation(
            description = "Get all registered users - user must be an admin or pastor",
            summary = "Returns all registered users - must be logged in as admin"
    )
    @GetMapping("/all-users")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @Operation(
            description = "Updates registered users information",
            summary = "Enables user update profile information"
    )
    @PatchMapping("/update-user-information")
    public ResponseEntity<String> updateUserInformation(
            @Valid @RequestBody UpdateUserInformation userInformation,
            @CurrentUser UserPrincipal currentUser) throws IOException {
        return userService.updateUserInformation(userInformation, currentUser);

    }


    @Operation(
            description = "Gives the role of a cell leader",
            summary = "Gives the role of a cell leader - must be logged in as admin or pastor"
    )
    @PatchMapping("/make-cell-leader/{userId}/{cellUnitId}")
    @PreAuthorize("hasRole('ROLE_ADMIN__USER')")
    public ResponseEntity<String> makeCellLeader(@PathVariable(value = "userId") UUID userId,
                                                 @PathVariable UUID cellUnitId){
        return userService.makeCellLeader(userId, cellUnitId);
    }

}
