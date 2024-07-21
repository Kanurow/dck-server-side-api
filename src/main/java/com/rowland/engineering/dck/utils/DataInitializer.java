package com.rowland.engineering.dck.utils;

import com.rowland.engineering.dck.exception.AppException;
import com.rowland.engineering.dck.model.*;
import com.rowland.engineering.dck.repository.CellUnitRepository;
import com.rowland.engineering.dck.repository.RoleRepository;
import com.rowland.engineering.dck.repository.UserRepository;
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
    private final CellUnitRepository cellUnitRepository;
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
        List<Role> roles1 = roleRepository.saveAll(roles);
        //        persistUsers();
    }

    private void persistUsers() {

        Role roleUser = roleRepository.findByName("ROLE_MEMBER")
                .orElseThrow(() -> new AppException("User Role not set."));
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new AppException("Admin Role not set."));

        User user1 = User.builder()
                .id(UUID.randomUUID())
                .firstName("Rowland")
                .lastName("Kanu")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(2007, 11,20))
                .email("kanurowland92@gmail.com")
                .branchChurch("Kubwa")
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
                .branchChurch("Utako")
                .phoneNumber("012345678")
                .password(passwordEncoder.encode("flames"))
                .build();

        user1.setRoles(new HashSet<>(List.of(roleUser, roleAdmin)));
        user2.setRoles(new HashSet<>(List.of(roleAdmin)));


        userRepository.saveAll(List.of(
                user1, user2));
    }
}
