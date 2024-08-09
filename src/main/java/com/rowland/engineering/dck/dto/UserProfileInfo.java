package com.rowland.engineering.dck.dto;

import com.rowland.engineering.dck.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileInfo {

    private UUID id;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    private String alternativePhoneNumber;

    private String favouriteBiblePassage;
    private String profileImageUrl;
    private Double walletBalance;

    private Set<Role> roles = new HashSet<>();

    private Set<BranchChurch> branchChurch = new HashSet<>();

    private Set<Department> departments = new HashSet<>();
}
