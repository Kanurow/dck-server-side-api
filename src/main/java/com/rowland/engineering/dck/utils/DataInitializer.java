package com.rowland.engineering.dck.utils;

import com.rowland.engineering.dck.exception.AppException;
import com.rowland.engineering.dck.model.*;
import com.rowland.engineering.dck.repository.*;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final BranchChurchRepository branchChurchRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = Arrays.asList(
                new Role(RoleName.ROLE_MEMBER),
                new Role(RoleName.ROLE_ADMIN__USER),
                new Role(RoleName.ROLE_PASTOR),
                new Role(RoleName.ROLE_CELL_LEADER),
                new Role(RoleName.ROLE_WORKER)
        );
        roleRepository.saveAll(roles);

        List<Department> departments = Arrays.asList(
                new Department(DepartmentName.ADMIN),
                new Department(DepartmentName.CHOIR),
                new Department(DepartmentName.GREETERS),
                new Department(DepartmentName.FACILITY_MANAGEMENT),
                new Department(DepartmentName.INTERCESSORY),
                new Department(DepartmentName.PROTOCOL),
                new Department(DepartmentName.SANCTUARY),
                new Department(DepartmentName.TECHNICAL),
                new Department(DepartmentName.MVPS),
                new Department(DepartmentName.USHERING),
                new Department(DepartmentName.KINGS_KIDS),
                new Department(DepartmentName.MEDIA),
                new Department(DepartmentName.MOBILIZATION)
        );
        departmentRepository.saveAll(departments);

        List<BranchChurch> branchChurches = Arrays.asList(
                new BranchChurch(BranchChurchName.FO1),
                new BranchChurch(BranchChurchName.ARAB_ROAD),
                new BranchChurch(BranchChurchName.FCDA),
                new BranchChurch(BranchChurchName.DUTSE_ARMY_SCHEME),
                new BranchChurch(BranchChurchName.GBAZANGO),
                new BranchChurch(BranchChurchName.PHASE_4)

        );
        branchChurchRepository.saveAll(branchChurches);
//                persistUsers();
    }

    private void persistUsers() {

        Role roleMember = roleRepository.findByName("ROLE_MEMBER")
                .orElseThrow(() -> new AppException("User Role not set."));
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new AppException("Admin Role not set."));
        Department technicalDepartment = departmentRepository.findByDepartmentName("TECHNICAL")
                .orElseThrow(() -> new AppException("Technical department not set."));
        Department mediaDepartment = departmentRepository.findByDepartmentName("MEDIA")
                .orElseThrow(() -> new AppException("Media department not set."));

        BranchChurch branchChurch = branchChurchRepository.findByBranchName("FO1")
                .orElseThrow(() -> new AppException("Branch church not set."));

        User user1 = User.builder()
                .id(UUID.randomUUID())
                .firstName("Rowland")
                .lastName("Kanu")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(2007, 11,20))
                .email("kanurowland92@gmail.com")
                .phoneNumber("08143358911")
                .favouriteBiblePassage("John 16:33 - For the Spirit God gave us does not make us timid, but gives us power, love and self-discipline.")
                .password(passwordEncoder.encode("flames"))
                .build();

        User user2 = User.builder()
                .id(UUID.randomUUID())
                .firstName("Juliet")
                .lastName("Kanu")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(1999, 4,15))
                .email("kanujuliet@gmail.com")
                .phoneNumber("012345678")
                .password(passwordEncoder.encode("flames"))
                .build();

        user1.setRoles(new HashSet<>(List.of(roleMember, roleAdmin)));
        user1.setBranchChurch(new HashSet<>(List.of(branchChurch)));
        user1.setDepartment(new HashSet<>(List.of(technicalDepartment)));

        user2.setRoles(new HashSet<>(List.of(roleMember, roleAdmin)));
        user2.setBranchChurch(new HashSet<>(List.of(branchChurch)));
        user2.setDepartment(new HashSet<>(List.of(technicalDepartment, mediaDepartment)));

        userRepository.saveAll(List.of(
                user1, user2));
    }
}
