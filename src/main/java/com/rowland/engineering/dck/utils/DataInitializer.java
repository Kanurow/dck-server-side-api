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


    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = Arrays.asList(
                new Role("MEMBER"),
                new Role("ROLE_PASTOR"),
                new Role("CELL_LEADER"),
                new Role("ADMIN"),
                new Role("WORKER")
        );
        roleRepository.saveAll(roles);

        List<Department> departments = Arrays.asList(
                new Department("ADMIN"),
                new Department("CHOIR"),
                new Department("GREETERS"),
                new Department("FACILITY_MANAGEMENT"),
                new Department("INTERCESSORY"),
                new Department("PROTOCOL"),
                new Department("SANCTUARY"),
                new Department("TECHNICAL"),
                new Department("MVPS"),
                new Department("USHERING"),
                new Department("KINGS_KIDS"),
                new Department("MEDIA"),
                new Department("MOBILIZATION")
        );
        departmentRepository.saveAll(departments);

        List<BranchChurch> branchChurches = Arrays.asList(
                new BranchChurch("FO1"),
                new BranchChurch("ARAB_ROAD"),
                new BranchChurch("FCDA"),
                new BranchChurch("DUTSE_ARMY_SCHEME"),
                new BranchChurch("GBAZANGO"),
                new BranchChurch("PHASE_4")

        );
        branchChurchRepository.saveAll(branchChurches);
    }
}
