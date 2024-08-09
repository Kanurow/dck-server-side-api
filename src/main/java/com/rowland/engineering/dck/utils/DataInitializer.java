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
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final BranchChurchRepository branchChurchRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = Arrays.asList(
                new Role("MEMBER"),
                new Role("PASTOR"),
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
        persistUsers();
    }

    private void persistUsers() {

        Role roleAdmin = roleRepository.findByName("ADMIN");
        Role rolePastor = roleRepository.findByName("PASTOR");
        BranchChurch branchChurchFCDA = branchChurchRepository.findByName("FCDA");
        BranchChurch branchChurchFO1 = branchChurchRepository.findByName("FO1");

        Set<BranchChurch> user1BranchChurch = new HashSet<>();
        user1BranchChurch.add(branchChurchFCDA);
        Set<BranchChurch> user2BranchChurch = new HashSet<>();
        user2BranchChurch.add(branchChurchFO1);

        Department departmentTECHNICAL = departmentRepository.findByName("TECHNICAL");
        Department departmentMEDIA = departmentRepository.findByName("MEDIA");
        Department departmentUSHERING = departmentRepository.findByName("USHERING");

        Set<Department> user1Department = new HashSet<>();
        Set<Department> user2Department = new HashSet<>();
        user1Department.add(departmentMEDIA);
        user1Department.add(departmentTECHNICAL);
        user2Department.add(departmentUSHERING);


        User user1 = User.builder()
                .id(UUID.fromString("be84a53a-da8e-46f5-8abb-2f329fa66f6b"))
                .firstName("Rowland")
                .lastName("Kanu")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(2007, 11,20))
                .email("kanurowland92@gmail.com")
                .branchChurch(user1BranchChurch)
                .departments(user1Department)
                .phoneNumber("08143358911")
                .favouriteBiblePassage("John 16:33 - For the Spirit God gave us does not make us timid, but gives us power, love and self-discipline.")
                .password(passwordEncoder.encode("flames"))
                .build();

        User user2 = User.builder()
                .id(UUID.fromString("a61e7dc3-347a-46e9-a8df-927e9e22fa4c"))
                .firstName("Victor")
                .lastName("Ifeanyi")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(1999, 4,15))
                .email("Victor@gmail.com")
                .branchChurch(user2BranchChurch)
                .departments(user2Department)
                .phoneNumber("012345678")
                .password(passwordEncoder.encode("Victor"))
                .build();

        user1.setRoles(new HashSet<>(List.of(roleAdmin, rolePastor)));
        user2.setRoles(new HashSet<>(List.of(roleAdmin)));


        userRepository.saveAll(List.of(
                user1, user2));
    }
}
