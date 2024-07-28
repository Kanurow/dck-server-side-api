package com.rowland.engineering.dck.repository;

import com.rowland.engineering.dck.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface BranchChurchRepository extends JpaRepository<BranchChurch, UUID> {
    BranchChurch findByName(String branchChurch);

}