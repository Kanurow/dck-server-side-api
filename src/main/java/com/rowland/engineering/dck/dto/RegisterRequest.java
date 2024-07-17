package com.rowland.engineering.dck.dto;

import com.rowland.engineering.dck.model.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(min = 2,max = 40, message = "First name should be between two characters and forty characters")
    private String firstName;
    @NotBlank
    @Size(min = 2,max = 40, message = "Last name should be between two characters and forty characters")
    private String lastName;

    private LocalDate dateOfBirth;

    @NotNull
    private Gender gender;

    @NotBlank(message ="Username must not be blank")
    @Size(min = 2,max = 40 , message = "Last name should be between two characters and forty characters")
    private String phoneNumber;

    @NotBlank
    private String branchChurch;

    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message ="Password must not be blank")
    @Size(min = 4)
    private String password;

    @NotBlank(message ="Password must not be blank")
    @Size(min = 4)
    private String confirmPassword;
}
