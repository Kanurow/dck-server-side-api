package com.rowland.engineering.dck.dto;

import com.rowland.engineering.dck.model.BranchChurch;
import com.rowland.engineering.dck.model.Department;
import com.rowland.engineering.dck.model.Gender;
import com.rowland.engineering.dck.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsResponse {
    private UUID id;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    private String alternativePhoneNumber;

    private boolean isCellLeader;

    private String favouriteBiblePassage;

    private Set<Role> roles = new HashSet<>();

    private Set<BranchChurch> branchChurch = new HashSet<>();

    private Set<Department> departments = new HashSet<>();

    private UUID cellUnitId;
}
