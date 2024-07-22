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
    }
}
